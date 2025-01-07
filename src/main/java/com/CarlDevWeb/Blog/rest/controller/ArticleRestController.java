package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.model.Article;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{titre}")
    public ResponseEntity<ArticleDto> getByTitre(@PathVariable("titre") String titre) {
        try {
            Article article = articleService.findByTitreContainingIgnoreCase(titre);
            if (article == null) {
                return ResponseEntity.notFound().build();
            }

           ArticleDto articleDto = articleMapper.toDto(article);
            return ResponseEntity.ok(articleDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.articleService.deleteById(id);
    }


}
