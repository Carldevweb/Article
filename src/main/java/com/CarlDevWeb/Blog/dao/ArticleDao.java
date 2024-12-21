package com.CarlDevWeb.Blog.dao;

import com.CarlDevWeb.Blog.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleDao extends CrudRepository<Article, Long> {

    List<Article> findByTitreContainingIgnoreCase(String nom);
}
