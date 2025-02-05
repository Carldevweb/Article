package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.CommentaireDto;
import com.CarlDevWeb.Blog.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/commentaires")
@RestController
public class CommentaireRestController {

    @Autowired
    private CommentaireService commentaireService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommentaireDto> creerCommentaire(@RequestBody CommentaireDto commentaireDto) {
        CommentaireDto nouveauCommentaire = commentaireService.creerCommentaire(commentaireDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauCommentaire);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentaireDto> commentaireById(@PathVariable Long id) {
        return commentaireService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.commentaireService.supprimerParId(id);
    }
}
