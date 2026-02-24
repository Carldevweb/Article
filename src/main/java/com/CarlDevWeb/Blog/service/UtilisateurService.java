package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.ProfilDto;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.mapper.UtilisateurMapper;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    // ⚠️ Si tu gardes cet endpoint POST /utilisateurs, il ne doit PAS créer un user avec mot de passe vide.
    // Idéalement : tu supprimes ce POST et tu crées les users via /auth/inscription.
    @Transactional
    public ProfilDto creerUtilisateurProfil(ProfilDto dto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setPrenomUtilisateur(dto.getPrenomUtilisateur());
        utilisateur.setEmail(dto.getEmail());
        // role/motDePasse/token : non gérés ici

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toProfilDto(saved);
    }

    @Transactional
    public void supprimerParId(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable avec l'id : " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    public Optional<ProfilDto> rechercherParNom(String nom) {
        return utilisateurRepository.findByNomUtilisateurContainingIgnoreCase(nom)
                .stream()
                .findFirst()
                .map(utilisateurMapper::toProfilDto);
    }

    public Optional<ProfilDto> findById(Long id) {
        return utilisateurRepository.findById(id)
                .map(utilisateurMapper::toProfilDto);
    }

    @Transactional
    public ProfilDto mettreAJourProfil(Long id, ProfilDto dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));

        utilisateurMapper.updateEntityFromProfilDto(utilisateur, dto);

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toProfilDto(saved);
    }
}
