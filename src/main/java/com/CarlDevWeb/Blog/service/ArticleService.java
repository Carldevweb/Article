package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;


    @Transactional
    public Article save(Article article) {
        article.setDateCreation(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public void deleteById(Long id) {
        this.articleRepository.deleteById(id);
    }

    public Article findByTitreContainingIgnoreCase(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            System.out.println("Le titre n'existe pas");
        }
        return (Article) articleRepository.findByTitreContainingIgnoreCase(titre);
    }
}
