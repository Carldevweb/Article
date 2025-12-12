package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.mapper.ArticleMapper;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<ArticleDto> creerArticle(@RequestBody ArticleDto articleDto) {
        ArticleDto sauvegarderArticle = articleService.creerArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderArticle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable("id") Long id) {
        return articleService.findById(id)
                .map(articleMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAll() {
        List<ArticleDto> dtos = articleService.findAll();
        return ResponseEntity.ok(dtos);
    }



    @GetMapping("/titre/{titre}")
    public ResponseEntity<ArticleDto> getByTitre(@PathVariable("titre") String titre) {
        return articleService.findByTitreContainingIgnoreCase(titre) // Optional<ArticleDto>
                .map(ResponseEntity::ok)                             // renvoie 200 + ArticleDto
                .orElseGet(() -> ResponseEntity.notFound().build()); // renvoie 404
    }



    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
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


    @PreAuthorize("hasRole('ADMIN')")
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
