package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping
    public ResponseEntity<ArticleDto> creerArticle(@RequestBody ArticleDto articleDto) {
        ArticleDto sauvegarderArticle = articleService.creerArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderArticle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable ArticleDto articleDto) {
        ArticleDto creerArticle = articleService.creerArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creerArticle);
    }

    @GetMapping("titre/{titre}")
    public ResponseEntity<List<ArticleDto>> getByTitre(@PathVariable("titre") String titre) {
        Optional<ArticleDto> articleDto = articleService.findByTitreContainingIgnoreCase(titre);
        if (articleDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/listes")
    public ResponseEntity<List<ArticleDto>> getAll() {
        List<ArticleDto> dtos = articleService.findAll();
        return ResponseEntity.ok(dtos);
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
    public ResponseEntity<Void> supprimerParId(@PathVariable("id") Long id) {
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ArticleDto>> getParCategories(@RequestParam List<String> categories) {
        List<ArticleDto> articles = articleService.articleParCategories(categories);

        if (articles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        return ResponseEntity.ok(articles);
    }
}
