package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.PersonneDto;
import com.CarlDevWeb.Blog.mapper.PersonneMapper;
import com.CarlDevWeb.Blog.model.Personne;
import com.CarlDevWeb.Blog.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personne")
public class PersonneRestController {


    @Autowired
    private PersonneService personneService;

    @Autowired
    private PersonneMapper personneMapper;

    @PostMapping
    public ResponseEntity<PersonneDto> creerPersonne(@RequestBody PersonneDto personneDto) {
        PersonneDto sauvegardePersonne = personneService.creerPersonne(personneDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sauvegardePersonne);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<Personne> PersonneParNom(@PathVariable String nom) {
        try {
            List<PersonneDto> personne = personneService.rechercherParNom(nom);

            if (personne.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok((Personne) personne);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personne> mettreAJourPersonne(@PathVariable long id, @RequestBody Personne personneDetails) {
        try {
            Optional<Personne> personneExistante = personneService.findById(id);

            if (personneExistante.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Personne personneAMettreAJour = personneExistante.get();
            personneAMettreAJour.setNomPersonne(personneDetails.getNomPersonne());
            personneAMettreAJour.setPrenomPersonne(personneDetails.getPrenomPersonne());
            personneAMettreAJour.setEmail(personneAMettreAJour.getEmail());
            personneAMettreAJour.setMotDePasse(personneAMettreAJour.getMotDePasse());

            return ResponseEntity.ok(personneAMettreAJour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
