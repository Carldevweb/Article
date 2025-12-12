package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/categories")
public class CategorieRestController {

    private final static Logger LOGGER =
            Logger.getLogger(CategorieRestController.class.getName());

    @Autowired
    private CategorieService categorieService;

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


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParId(@PathVariable("id") Long id) {
        categorieService.supprimerParId(id);
        return ResponseEntity.noContent().build();
    }
}
