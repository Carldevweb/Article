package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Media;
import com.CarlDevWeb.Blog.mapper.MediaMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MediaMapper mediaMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Transactional
    public MediaDto enregisterMedia(MediaDto mediaDto) {
        Article article = articleRepository.findById(mediaDto.getArticle().getId())
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
        Media mediaExistant = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Média inexistant"));

        mediaExistant.setUrl(mediaDto.getUrl());
        mediaExistant.setType(mediaDto.getType());
        mediaExistant.setArticle(mediaExistant.getArticle());

        Media mediaMisAJour = mediaRepository.save(mediaExistant);
        return mediaMapper.toDto(mediaMisAJour);

    }

    public void supprimerMedia(Long id) {
        this.mediaRepository.deleteById(id);
    }
}
