package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dao.ArticleDao;
import com.CarlDevWeb.Blog.model.Article;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;


    @Transactional
    public Article save(Article article) {
        article.setDateCreation(LocalDateTime.now());
        return articleDao.save(article);
    }

    public void deleteById(Long id) {
        this.articleDao.deleteById(id);
    }

    public Article findByTitreContainingIgnoreCase(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            System.out.println("Le titre n'existe pas");
        }
        return (Article) articleDao.findByTitreContainingIgnoreCase(titre);
    }
}
