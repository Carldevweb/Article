package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.dto.UploadMediaResponse;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Media;
import com.CarlDevWeb.Blog.mapper.MediaMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final ArticleRepository articleRepository;

    public MediaService(MediaRepository mediaRepository,
                        MediaMapper mediaMapper,
                        ArticleRepository articleRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.articleRepository = articleRepository;
    }

    /**
     * ✅ Récupérer tous les médias d’un article
     * Utilisé par GET /medias/article/{articleId}
     */
    public List<MediaDto> findByArticleId(Long articleId) {
        if (articleId == null) {
            throw new IllegalArgumentException("articleId est obligatoire");
        }

        return mediaRepository.findByArticleIdOrderByIdAsc(articleId)
                .stream()
                .map(mediaMapper::toDto)
                .toList();
    }

    @Transactional
    public MediaDto enregisterMedia(MediaDto mediaDto) {
        if (mediaDto == null || mediaDto.getArticleId() == null) {
            throw new IllegalArgumentException("articleId est obligatoire");
        }

        Article article = articleRepository.findById(mediaDto.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("L'article n'existe pas"));

        Media media = new Media();
        media.setArticle(article);
        media.setType(mediaDto.getType());
        media.setUrl(mediaDto.getUrl());

        Media mediaSauvegarde = mediaRepository.save(media);
        return mediaMapper.toDto(mediaSauvegarde);
    }

    @Transactional
    public MediaDto miseAJourMedia(Long id, MediaDto mediaDto) {
        if (id == null) {
            throw new IllegalArgumentException("id est obligatoire");
        }
        if (mediaDto == null) {
            throw new IllegalArgumentException("mediaDto est obligatoire");
        }

        Media mediaExistant = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Média inexistant"));

        mediaExistant.setUrl(mediaDto.getUrl());
        mediaExistant.setType(mediaDto.getType());

        if (mediaDto.getArticleId() != null) {
            Article article = articleRepository.findById(mediaDto.getArticleId())
                    .orElseThrow(() -> new IllegalArgumentException("L'article n'existe pas"));
            mediaExistant.setArticle(article);
        }

        Media mediaMisAJour = mediaRepository.save(mediaExistant);
        return mediaMapper.toDto(mediaMisAJour);
    }

    public UploadMediaResponse uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        Path uploadDir = Paths.get("uploads");
        try {
            Files.createDirectories(uploadDir);

            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) ext = original.substring(dot);

            String filename = UUID.randomUUID() + ext;
            Path target = uploadDir.resolve(filename);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // ⚠️ URL publique : expose /uploads via ResourceHandler (si pas déjà fait)
            String url = "http://localhost:8080/uploads/" + filename;

            return UploadMediaResponse.builder()
                    .filename(filename)
                    .url(url)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Upload échoué", e);
        }
    }

    public void supprimerMedia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id est obligatoire");
        }
        mediaRepository.deleteById(id);
    }
}