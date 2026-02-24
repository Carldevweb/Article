package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.*;
import com.CarlDevWeb.Blog.entity.PasswordResetToken;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.enums.Role;
import com.CarlDevWeb.Blog.repository.PasswordResetTokenRepository;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import com.CarlDevWeb.Blog.securite.JwtUtil;
import com.CarlDevWeb.Blog.securite.UtilisateurDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthentificationService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurDetailsServiceImpl userDetailsService;

    @Autowired
    private EmailService emailService;

    public AuthentificationReponseDto connexion(AuthentificationRequeteDto requete) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requete.getEmail(), requete.getMotDePasse())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(requete.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthentificationReponseDto(token);
    }

    @Transactional
    public InscriptionReponseDto inscription(InscriptionRequeteDto requete) {

        String email = requete.getEmail() == null ? null : requete.getEmail().trim();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email invalide");
        }

        if (utilisateurRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("L'email est déjà utilisé.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(requete.getNomUtilisateur() != null ? requete.getNomUtilisateur().trim() : null);
        utilisateur.setPrenomUtilisateur(requete.getPrenomUtilisateur() != null ? requete.getPrenomUtilisateur().trim() : null);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(passwordEncoder.encode(requete.getMotDePasse()));
        utilisateur.setRole(Role.USER);


        Utilisateur saved = utilisateurRepository.save(utilisateur);

        InscriptionReponseDto reponse = new InscriptionReponseDto();
        reponse.setId(saved.getId());
        reponse.setEmail(saved.getEmail());
        reponse.setNomUtilisateur(saved.getNomUtilisateur());
        reponse.setRole(saved.getRole().name());

        return reponse;
    }

    public MotDePasseOublieReponseDto demanderMotDePasseOublie(String email) {

        if (email == null || email.isBlank()) {
            return new MotDePasseOublieReponseDto(false, "Email invalide.");
        }

        utilisateurRepository.findByEmailIgnoreCase(email).ifPresent(utilisateur -> {

            passwordResetTokenRepository.findFirstByUtilisateurAndUsedAtIsNullOrderByExpiresAtDesc(utilisateur)
                    .ifPresent(existing -> {
                        existing.setUsedAt(LocalDateTime.now());
                        passwordResetTokenRepository.save(existing);
                    });

            String resetToken = UUID.randomUUID().toString();

            PasswordResetToken prt = new PasswordResetToken();
            prt.setToken(resetToken);
            prt.setUtilisateur(utilisateur);
            prt.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            prt.setUsedAt(null);

            passwordResetTokenRepository.save(prt);

            emailService.envoyerResetPassword(utilisateur.getEmail(), resetToken);
        });

        return new MotDePasseOublieReponseDto(true, "Si l'email existe, un message a été envoyé.");
    }

    public ReinitialiserMdpReponseDto reinitialiserMdp(ReinitialiserMdpRequeteDto requete) {

        PasswordResetToken prt = passwordResetTokenRepository.findByToken(requete.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token invalide"));

        if (prt.isUsed()) {
            throw new IllegalArgumentException("Token déjà utilisé");
        }

        if (prt.isExpired()) {
            throw new IllegalArgumentException("Token expiré");
        }

        Utilisateur utilisateur = prt.getUtilisateur();
        utilisateur.setMotDePasse(passwordEncoder.encode(requete.getNouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);

        prt.setUsedAt(LocalDateTime.now());
        passwordResetTokenRepository.save(prt);

        return new ReinitialiserMdpReponseDto(true, "Le mot de passe a été changé avec succès");
    }

    public ReinitialiserEmailReponseDto reinitialiserEmail(ReinitialiserEmailRequeteDto requete) {
        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(requete.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("L'email est invalide ou n'existe pas"));

        utilisateur.setEmail(requete.getEmail());
        utilisateurRepository.save(utilisateur);

        return new ReinitialiserEmailReponseDto("L'email a été réinitialiser", true);
    }

    public String renouvelerToken(String authorizationHeader) {
        try {
            return jwtUtil.renewFromAuthorizationHeader(authorizationHeader);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Le token ne peut pas être renouvelé");
        }
    }

}
