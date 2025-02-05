package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTitreContainingIgnoreCase(String titre);
}
