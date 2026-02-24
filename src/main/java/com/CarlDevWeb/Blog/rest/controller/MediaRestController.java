package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medias")
public class MediaRestController {

    @Autowired
    private MediaService mediaService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> upload(@RequestPart("file") MultipartFile file) {
        var res = mediaService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "filename", res.getFilename(),
                "url", res.getUrl()
        ));
    }

    @PostMapping
    public ResponseEntity<MediaDto> ajouterMedia(@RequestBody MediaDto mediaDto) {
        MediaDto sauvegarderMedia = mediaService.enregisterMedia(mediaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegarderMedia);
    }

    // ✅ AJOUT : récupérer les médias d’un article
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<MediaDto>> getByArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(mediaService.findByArticleId(articleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> mettreAJourMedia(@PathVariable("id") Long id,
                                                     @RequestBody MediaDto mediaDto) {
        try {
            MediaDto mediaMisAJour = mediaService.miseAJourMedia(id, mediaDto);
            return ResponseEntity.ok(mediaMisAJour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMedia(@PathVariable("id") Long id) {
        mediaService.supprimerMedia(id);
        return ResponseEntity.noContent().build();
    }
}