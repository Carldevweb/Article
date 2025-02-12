package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto) {
        ArticleDto sauvegarderArticle = articleService.creerArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderArticle);
    }

    @GetMapping("id/{id}")
    public Optional<Article> findById(@RequestBody Long id) {
        return articleService.findById(id);
    }

    @GetMapping("titre/{titre}")
    public ResponseEntity<ArticleDto> getByTitre(@PathVariable("titre") String titre) {

        Optional<ArticleDto> articleDto = articleService.findByTitreContainingIgnoreCase(titre);
        if (articleDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return articleDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> mettreAJour(@PathVariable("id") Long id,
                                                  @RequestBody ArticleDto articleDto) {

        if (id == null || articleDto == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ArticleDto articleMisAJour = articleService.mettreAJourArticle(id, articleDto);
            return ResponseEntity.ok(articleMisAJour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.articleService.deleteById(id);
    }
}
