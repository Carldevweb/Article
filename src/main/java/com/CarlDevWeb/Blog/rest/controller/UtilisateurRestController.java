package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.ProfilDto;
import com.CarlDevWeb.Blog.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurRestController {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(UtilisateurRestController.class));

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ProfilDto> getParId(@PathVariable Long id) {
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<ProfilDto> utilisateurParNom(@PathVariable String nom) {
        return utilisateurService.rechercherParNom(nom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/editer/{id}")
    public ResponseEntity<ProfilDto> mettreAJourProfil(@PathVariable Long id, @RequestBody ProfilDto dto) {
        try {
            ProfilDto updated = utilisateurService.mettreAJourProfil(id, dto);
            LOGGER.info("Profil mis à jour pour l'utilisateur id=" + id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParId(@PathVariable Long id) {
        utilisateurService.supprimerParId(id);
        return ResponseEntity.noContent().build();
    }
}
