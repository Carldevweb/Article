package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.entity.Media;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.CategorieRepository;
import com.CarlDevWeb.Blog.repository.MediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Transactional
    public ArticleDto creerArticle(ArticleDto articleDto, String email) {

        Article article = articleMapper.toEntity(articleDto);
        article.setAuteur(email);
        article.setDateCreation(LocalDateTime.now());

        appliquerCategories(article, articleDto.getCategoriesIds());

        Article saved = articleRepository.save(article);
        return enrichirDto(saved);
    }

    @Transactional
    public ArticleDto findDtoById(Long id) {
        Article article = articleRepository.findByIdWithMediaAndCategories(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article introuvable"));

        return enrichirDto(article);
    }

    @Transactional
    public ArticleDto getById(Long id) {
        Article article = articleRepository.findByIdWithMediaAndCategories(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article introuvable"));

        return enrichirDto(article);
    }

    @Transactional
    public ArticleDto mettreAJourArticle(Long id, ArticleDto articleDto) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé."));

        article.setTitre(articleDto.getTitre());
        article.setContenu(articleDto.getContenu());
        article.setMiseAJour(new Date());

        appliquerCategories(article, articleDto.getCategoriesIds());

        Article saved = articleRepository.save(article);
        return enrichirDto(saved);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findByIdWithCategories(id);
    }

    @Transactional
    public ArticleDto getByIdDto(Long id) {
        Article article = articleRepository.findByIdWithMediaAndCategories(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article introuvable"));

        return enrichirDto(article);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ArticleDto> findAll() {

        List<Article> articles = articleRepository.findAllWithCategories();

        java.util.Map<Long, Integer> counts = articleRepository.countFavoriByArticleId()
                .stream()
                .collect(java.util.stream.Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Long) row[1]).intValue()
                ));

        return articles.stream()
                .map(article -> {
                    ArticleDto dto = enrichirDto(article);
                    dto.setFavoriNb(counts.getOrDefault(article.getId(), 0));
                    return dto;
                })
                .toList();
    }

    public Optional<ArticleDto> findByTitreContainingIgnoreCase(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            return Optional.empty();
        }

        return articleRepository.findByTitreContainingIgnoreCase(titre)
                .stream()
                .findFirst()
                .map(this::enrichirDto);
    }

    public List<ArticleDto> articleParCategories(List<String> nomsCategorie) {

        if (nomsCategorie == null || nomsCategorie.isEmpty()) {
            throw new IllegalArgumentException("La liste des catégories ne peut pas être vide.");
        }

        List<Categorie> categories =
                categorieRepository.findByNomCategorieIn(nomsCategorie);

        return articleRepository.findByCategoriesIn(categories)
                .stream()
                .map(this::enrichirDto)
                .collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ArticleDto> getArticlesDtoByCategorie(Long categorieId) {
        return articleRepository.findByCategorieIdWithCategories(categorieId)
                .stream()
                .map(this::enrichirDto)
                .toList();
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    private void appliquerCategories(Article article, List<Long> categoriesIds) {
        article.getCategories().clear();

        if (categoriesIds == null || categoriesIds.isEmpty()) return;

        List<Categorie> categories = categorieRepository.findAllById(categoriesIds);
        article.getCategories().addAll(categories);
    }

    private ArticleDto enrichirDto(Article article) {
        ArticleDto dto = articleMapper.toDto(article);

        // categoriesIds
        if (article.getCategories() != null && !article.getCategories().isEmpty()) {
            dto.setCategoriesIds(
                    article.getCategories()
                            .stream()
                            .map(Categorie::getId)
                            .toList()
            );
        }

        // ✅ mainImageUrl : Set-compatible (plus de get(0))
        String mainUrl = null;

        if (article.getMedia() != null && !article.getMedia().isEmpty()) {
            mainUrl = article.getMedia().stream()
                    .filter(m -> m.getUrl() != null && !m.getUrl().isBlank())
                    .sorted(java.util.Comparator.comparing(Media::getId, java.util.Comparator.nullsLast(Long::compareTo)))
                    .map(Media::getUrl)
                    .findFirst()
                    .orElse(null);
        }

        // Fallback (si article.getMedia() pas chargé ou vide)
        if (mainUrl == null && article.getId() != null) {
            mainUrl = mediaRepository.findFirstByArticleIdOrderByIdAsc(article.getId())
                    .map(Media::getUrl)
                    .orElse(null);
        }

        dto.setMainImageUrl(mainUrl);
        return dto;
    }

    public ArticleDto toDtoWithCategories(Article article) {
        return enrichirDto(article);
    }
}
