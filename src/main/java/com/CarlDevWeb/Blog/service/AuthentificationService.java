package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.*;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import com.CarlDevWeb.Blog.securite.JwtUtil;
import com.CarlDevWeb.Blog.securite.UtilisateurDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthentificationService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private UtilisateurDetailsServiceImpl userDetailsService;

    public AuthentificationReponseDto connexion(AuthentificationRequeteDto requete) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requete.getEmail(), requete.getMotDePasse())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(requete.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        // Retour de la réponse avec le token
        return new AuthentificationReponseDto(token);
    }

    public AuthentificationReponseDto inscription(InscriptionRequeteDto requete) {
        if (utilisateurRepository.existsByEmail(requete.getEmail())) {
            throw new RuntimeException("L'email est déjà utilisé.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(requete.getNomUtilisateur());
        utilisateur.setEmail(requete.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(requete.getMotDePasse()));

        utilisateurRepository.save(utilisateur);

        UserDetails userDetails = userDetailsService.loadUserByUsername(requete.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthentificationReponseDto(token);
    }

    public ReinitialiserMdpReponseDto reinitialiserMdp(ReinitialiserMdpRequeteDto requete) {

        Utilisateur utilisateur = utilisateurRepository.findByToken(requete.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token invalide ou expiré"));

        utilisateur.setMotDePasse(passwordEncoder.encode(requete.getNouveauMotDePasse()));

        utilisateurRepository.save(utilisateur);

        return new ReinitialiserMdpReponseDto(true, "Le mot de passe a été changé avec succès");

    }

    public ReinitialiserEmailReponseDto reinitialiserEmail(ReinitialiserEmailRequeteDto requete) {

        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(requete.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("L'email est invalide ou n'existe pas"));

        utilisateur.setEmail(requete.getEmail());

        utilisateurRepository.save(utilisateur);

        return new ReinitialiserEmailReponseDto("L'email a été réinitialiser", true);
    }

    public boolean reinitialiserMdpViaToken(String token, String nouveauMotDePasse) {
        String email = jwtUtil.extractEmail(token);
        if (email != null) {
            Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmailIgnoreCase(email);
            if (utilisateurOpt.isPresent()) {
                Utilisateur utilisateur = utilisateurOpt.get();
                utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
                utilisateurRepository.save(utilisateur);
                return true;
            }
        }
        return false;
    }

    public String renouvelerToken(String ancienToken) {
        try {
            // 1) On enlève le préfixe
            String tokenSansPrefixe = ancienToken.replace("Bearer ", "");
            // 2) On récupère l'email depuis le token
            String email = jwtUtil.extractEmail(tokenSansPrefixe);

            // 3) On recharge les infos utilisateur (pour valider le rôle, etc.)
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 4) On génère un nouveau token à partir de l'email
            return jwtUtil.generateToken(email);
        } catch (Exception e) {
            System.out.println("Erreur lors du renouvellement du token : " + e.getMessage());
            throw new IllegalArgumentException("Le token ne peut pas être renouvelé");
        }
    }
}
