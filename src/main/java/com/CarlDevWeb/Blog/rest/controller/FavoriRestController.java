package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.service.FavoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favoris")
public class FavoriRestController {

    @Autowired
    FavoriService favoriService;

    @PostMapping
    public ResponseEntity<FavoriDto> ajouterFavori(@RequestBody FavoriDto favoriDto) {
        FavoriDto sauvegarderFavori = favoriService.ajouterFavori(favoriDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderFavori);
    }

    @DeleteMapping("/{id}")
    public void supprimerParId(@PathVariable("id") Long id) {
        this.favoriService.supprimerParId(id);
    }
}
