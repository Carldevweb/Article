package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleMapper articleMapper;


    @Transactional
    public ArticleDto creerArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        article.setDateCreation(LocalDateTime.now());
        Article sauvegardeArticle = articleRepository.save(article);

        return articleMapper.toDto(sauvegardeArticle);
    }

    @Transactional
    public ArticleDto mettreAJourArticle(Long id, ArticleDto articleDto) {
        Article articleExistant = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé."));

        articleExistant.setTitre(articleDto.getTitre());
        articleExistant.setContenu(articleDto.getContenu());
        articleExistant.setAuteur(articleDto.getAuteur());
        articleExistant.setMiseAJour(articleDto.getMiseAJour());

        Article articleMisAJour = articleRepository.save(articleExistant);
        return articleMapper.toDto(articleMisAJour);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        this.articleRepository.deleteById(id);
    }

    public Optional<ArticleDto> findByTitreContainingIgnoreCase(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            System.out.println("Le titre n'existe pas");
        }
        return articleRepository.findByTitreContainingIgnoreCase(titre)
                .stream()
                .findFirst()
                .map(articleMapper::toDto);
    }

    public List<ArticleDto> articleParCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("La liste des catégories ne peut pas être vide.");
        }

        List<Article> articles = articleRepository.findByCategoriesIn(categories);

        return articles.stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }
}
