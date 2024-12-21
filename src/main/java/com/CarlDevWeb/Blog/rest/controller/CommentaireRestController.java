package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.model.Commentaire;
import com.CarlDevWeb.Blog.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommentaireRestController {

    @Autowired
    private CommentaireService commentaireService;

    @PostMapping("/commentaire")
    public ResponseEntity<Commentaire> save(@RequestBody Commentaire commentaire) {
        Commentaire sauvegardeCommentaire = commentaireService.save(commentaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegardeCommentaire);
    }

    @GetMapping("/commentaire/{id}")
    public ResponseEntity<Commentaire> commentaireById(@PathVariable Long id) {
        try {
            Optional<Commentaire> commentaire = commentaireService.findById(id);

            return commentaire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/article/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.commentaireService.deleteById(id);
    }
}
