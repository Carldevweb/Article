package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.model.Article;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<Article> save(@RequestBody Article article) {
        Article saveArticle = articleService.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }

    @GetMapping("/article/titre")
    public ResponseEntity<Article> getByTitre(@PathVariable("titre") String titre) {
        try {
            Article article = articleService.findByTitreContainingIgnoreCase(titre);
            return ResponseEntity.ok(article);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/article/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.articleService.deleteById(id);
    }


}
