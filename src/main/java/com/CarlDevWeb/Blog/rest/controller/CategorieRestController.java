package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.service.ArticleService;
import com.CarlDevWeb.Blog.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/categories")
public class CategorieRestController {

    private final static Logger LOGGER =
            Logger.getLogger(CategorieRestController.class.getName());

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategorieDto> creerCategorie(@RequestBody CategorieDto categorieDto) {
        try {
            CategorieDto sauvegarderCategorie = categorieService.creerCategorie(categorieDto);
            LOGGER.info("Nouvelle catégorie créée");
            return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderCategorie);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création de la catégorie : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CategorieDto>> getAll() {
        List<CategorieDto> categories = categorieService.listeCategorie();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDto> getParId(@PathVariable Long id) {
        CategorieDto dto = categorieService.getParId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/articles")
    public ResponseEntity<List<ArticleDto>> getArticlesParCategorie(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticlesDtoByCategorie(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDto> update(@PathVariable Long id,
                                               @RequestBody CategorieDto dto) {

        Categorie updated = categorieService.update(id, dto);

        // mapping Entity -> DTO
        CategorieDto response = toDto(updated);

        return ResponseEntity.ok(response);
    }

    private CategorieDto toDto(Categorie categorie) {
        CategorieDto dto = new CategorieDto();
        dto.setId(categorie.getId());
        dto.setNomCategorie(categorie.getNomCategorie());
        dto.setImageUrl(categorie.getImageUrl());

        // IMPORTANT : évite de renvoyer les articles ici si tu as des soucis Lazy / recursion
        dto.setArticles(null);

        return dto;
    }

    /**
     * Upload image catégorie -> stocke le fichier sur disque + sauvegarde imageUrl en DB
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<CategorieDto> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Option simple: tu peux filtrer basiquement
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            String uploadDir = "uploads/categories";
            Files.createDirectories(Paths.get(uploadDir));

            String originalName = file.getOriginalFilename() == null ? "image" : file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalName.replaceAll("\\s+", "_");

            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // URL relative servie par Spring static resources
            String imageUrl = "/uploads/categories/" + fileName;

            CategorieDto updated = categorieService.updateImageUrl(id, imageUrl);

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            LOGGER.severe("Erreur upload image catégorie : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParId(@PathVariable("id") Long id) {
        categorieService.supprimerParId(id);
        return ResponseEntity.noContent().build();
    }
}