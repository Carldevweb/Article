package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.*;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import com.CarlDevWeb.Blog.securite.JwtUtil;
import com.CarlDevWeb.Blog.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authentification")
public class AuthentificationRestController {

    @Autowired
    private AuthentificationService authentificationService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/connexion")
    public ResponseEntity<AuthentificationReponseDto> connexion(@RequestBody AuthentificationRequeteDto requete) {
        Optional<Utilisateur> utilisateur = Optional.ofNullable(utilisateurRepository.findByEmailIgnoreCase(requete.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable")));

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

    @PostMapping("/renouveler-token")
    public ResponseEntity<String> renouvelerToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Renouvellement du token via le service
            String nouveauToken = authentificationService.renouvelerToken(authorizationHeader);

            // Retourner le nouveau token avec un code de réponse 200 (OK)
            return ResponseEntity.ok(nouveauToken);
        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse avec un message d'erreur et un code de statut 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors du renouvellement du token : " + e.getMessage());
        }
    }

    @GetMapping("/profil")
    public ResponseEntity<Utilisateur> getProfilUtilisateur(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        return ResponseEntity.ok(utilisateur);
    }

}
