package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.*;
import com.CarlDevWeb.Blog.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentification")
public class AuthentificationRestController {

    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("/connexion")
    public ResponseEntity<AuthentificationReponseDto> connexion(@RequestBody AuthentificationRequeteDto requete) {
        AuthentificationReponseDto reponse = authentificationService.connexion(requete);

        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/inscription")
    public ResponseEntity<AuthentificationReponseDto> creerCompte(@RequestBody InscriptionRequeteDto inscriptionRequeteDto) {
        AuthentificationReponseDto reponse = authentificationService.inscription(inscriptionRequeteDto);
        return ResponseEntity.ok(reponse);

    }

    @PostMapping("/mot-de-passe-oublie")
    public ResponseEntity<Boolean> demanderReinitialisationMdp(@RequestBody MotDePasseOublieRequeteDto requete) {
        boolean reponse = authentificationService.reinitialiserMdpViaToken(requete.getTokenTemporaire(), requete.getNouveauMotDePasse());
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<ReinitialiserMdpReponseDto> reinitialiserMdp(@RequestBody ReinitialiserMdpRequeteDto reinitialiserMdpRequete) {
        ReinitialiserMdpReponseDto reponse = authentificationService.reinitialiserMdp(reinitialiserMdpRequete);
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/reinitialiser-email")
    public ResponseEntity<ReinitialiserEmailReponseDto> reinitialiserEmail(@RequestBody ReinitialiserEmailRequeteDto reinitilaiserEmailRequete) {
        ReinitialiserEmailReponseDto reponse = authentificationService.reinitialiserEmail(reinitilaiserEmailRequete);
        return ResponseEntity.ok(reponse);
    }

}
