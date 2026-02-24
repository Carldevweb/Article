package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ArticleDto> creerArticle(@RequestBody ArticleDto articleDto,
                                                   Authentication authentication) {

        String email = authentication.getName();
        ArticleDto sauvegarderArticle = articleService.creerArticle(articleDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderArticle);
    }

    /**
     * IMPORTANT : renvoie un DTO qui contient categoriesIds + medias
     * et utilise un fetch-join côté repository/service pour éviter LazyInitializationException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable Long id) {
        ArticleDto dto = articleService.getByIdDto(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAll() {
        List<ArticleDto> dtos = articleService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/titre/{titre}")
    public ResponseEntity<ArticleDto> getByTitre(@PathVariable("titre") String titre) {
        return articleService.findByTitreContainingIgnoreCase(titre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<ArticleDto> mettreAJour(@PathVariable("id") Long id,
                                                  @RequestBody ArticleDto articleDto,
                                                  Authentication authentication) {
        if (id == null || articleDto == null) {
            return ResponseEntity.badRequest().build();
        }

        ArticleDto articleMisAJour = articleService.mettreAJourArticle(id, articleDto);
        return ResponseEntity.ok(articleMisAJour);
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
