package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.UtilisateurDto;
import com.CarlDevWeb.Blog.mapper.UtilisateurMapper;
import com.CarlDevWeb.Blog.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurRestController {

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(UtilisateurRestController.class));

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @PostMapping
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto sauvegardePersonne = utilisateurService.creerPersonne(utilisateurDto);

            LOGGER.info("Nouvel utilisateur créé : " + sauvegardePersonne);

            return ResponseEntity.status(HttpStatus.CREATED).body(sauvegardePersonne);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création de l'utilisateur : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UtilisateurDto> getParId(@PathVariable Long id) {
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<UtilisateurDto> utilisateurParNom(@PathVariable String nom) {

        return utilisateurService.rechercherParNom(nom)
                .map(ResponseEntity::ok) // Si trouvé, retourne 200 avec le DTO
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Sinon 404
    }


    @PutMapping("editer/{id}")
    public ResponseEntity<UtilisateurDto> mettreAJourPersonne(@PathVariable long id, @RequestBody UtilisateurDto utilisateurDto) {

        return utilisateurService.findById(id)
                .map(utilisateurExistant -> {
                    utilisateurExistant.setNomUtilisateur(utilisateurDto.getNomUtilisateur());
                    utilisateurExistant.setPrenomUtilisateur(utilisateurDto.getPrenomUtilisateur());
                    utilisateurExistant.setEmail(utilisateurDto.getEmail());

                    UtilisateurDto utilsateurAJour = utilisateurService.modifierUtilsateur(utilisateurExistant);

                    return ResponseEntity.ok(utilsateurAJour);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParId(@PathVariable Long id) {
        this.utilisateurService.supprimerParId(id);
        return ResponseEntity.noContent().build();
    }

}
