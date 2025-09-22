package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/categories")
public class CategorieRestController {

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(UtilisateurRestController.class));

    @Autowired
    private CategorieService categorieService;

    @PostMapping
    public ResponseEntity<CategorieDto> creerCategorie(@RequestBody CategorieDto categorieDto) {

        try {
            CategorieDto sauvegarderCategorie = categorieService.creerCategorie(categorieDto);
            LOGGER.info("Nouvelle catégorie créée");
            return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderCategorie);

        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création de la catégorie");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategorieDto> getParId(@PathVariable Long id) {
        CategorieDto getParId = categorieService.getParId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(getParId);
    }

    @GetMapping("/listeCategorie")
    public List<CategorieDto> listeCategorie() {
        return categorieService.listeCategorie();
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> supprimerParId(@PathVariable("id") Long id) {
        categorieService.supprimerParId(id);
        return ResponseEntity.noContent().build();
    }
}
