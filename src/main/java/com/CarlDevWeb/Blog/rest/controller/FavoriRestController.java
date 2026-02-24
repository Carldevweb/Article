package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.FavoriCreateRequest;
import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.service.FavoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/favoris", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriRestController {

    @Autowired
    FavoriService favoriService;

    public FavoriRestController(FavoriService favoriService) {
        this.favoriService = favoriService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriDto> ajouterFavori(@RequestBody FavoriCreateRequest req,
                                                   Authentication authentication) {
        FavoriDto sauvegarderFavori = favoriService.ajouterFavori(req.getArticleId(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderFavori);
    }



    @GetMapping
    public ResponseEntity<List<FavoriDto>> getAll(Authentication authentication) {
        return ResponseEntity.ok(favoriService.findAllByEmail(authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParId(@PathVariable Long id,
                                               Authentication authentication) {
        favoriService.supprimerParId(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}

