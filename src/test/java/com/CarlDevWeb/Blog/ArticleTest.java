package com.CarlDevWeb.Blog;

import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void permierArticle() {
        Article article = new Article();
        article.setTitre("Essai");
        article.setContenu("permier essai");
        article.setAuteur("carldevweb");

        articleRepository.save(article);
        articleRepository.findById(article.getId());

    }
}
