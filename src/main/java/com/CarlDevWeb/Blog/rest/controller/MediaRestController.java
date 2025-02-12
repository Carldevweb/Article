package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("medias")
public class MediaRestController {

    @Autowired
    MediaService mediaService;


    @PostMapping
    public ResponseEntity<MediaDto> ajouterMedia(@RequestBody MediaDto mediaDto) {
        MediaDto sauvegarderMedia = mediaService.enregisterMedia(mediaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderMedia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> mettreAJourMedia(@PathVariable("id") Long id,
                                                     @RequestBody MediaDto mediaDto) {

        if (mediaDto == null || id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            MediaDto mediaMisAJour = mediaService.miseAJourMedia(id, mediaDto);
            return ResponseEntity.ok(mediaMisAJour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void supprimerMedia(@PathVariable("id") Long id) {
        this.mediaService.supprimerMedia(id);
    }
}
