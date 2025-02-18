package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.AuthentificationReponseDto;
import com.CarlDevWeb.Blog.dto.AuthentificationRequeteDto;
import com.CarlDevWeb.Blog.dto.InscriptionRequeteDto;
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

        // Chargement des détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(requete.getEmail());

        // Génération du token JWT
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
}
