package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.*;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.mapper.UtilisateurMapper;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import com.CarlDevWeb.Blog.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentification")
public class AuthentificationRestController {

    @Autowired
    private AuthentificationService authentificationService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @PostMapping("/connexion")
    public ResponseEntity<AuthentificationReponseDto> connexion(@RequestBody AuthentificationRequeteDto requete) {
        return ResponseEntity.ok(authentificationService.connexion(requete));
    }

    @PostMapping("/inscription")
    public ResponseEntity<InscriptionReponseDto> creerCompte(@RequestBody InscriptionRequeteDto dto) {
        return ResponseEntity.ok(authentificationService.inscription(dto));
    }

    @PostMapping("/mot-de-passe-oublie")
    public ResponseEntity<MotDePasseOublieReponseDto> motDePasseOublie(@RequestBody MotDePasseOublieRequeteDto requete) {
        return ResponseEntity.ok(authentificationService.demanderMotDePasseOublie(requete.getEmail()));
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<ReinitialiserMdpReponseDto> reinitialiserMdp(@RequestBody ReinitialiserMdpRequeteDto requete) {
        return ResponseEntity.ok(authentificationService.reinitialiserMdp(requete));
    }

    @PostMapping("/reinitialiser-email")
    public ResponseEntity<ReinitialiserEmailReponseDto> reinitialiserEmail(@RequestBody ReinitialiserEmailRequeteDto requete) {
        return ResponseEntity.ok(authentificationService.reinitialiserEmail(requete));
    }

    @PostMapping("/renouveler-token")
    public ResponseEntity<AuthentificationReponseDto> renouvelerToken(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String nouveauToken = authentificationService.renouvelerToken(authorizationHeader);
            return ResponseEntity.ok(new AuthentificationReponseDto(nouveauToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/profil")
    public ResponseEntity<ProfilDto> getProfilUtilisateur(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();

        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        return ResponseEntity.ok(utilisateurMapper.toProfilDto(utilisateur));
    }
}
