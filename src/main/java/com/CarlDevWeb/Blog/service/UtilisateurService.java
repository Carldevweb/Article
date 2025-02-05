package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.UtilisateurDto;
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

    @Transactional
    public UtilisateurDto creerPersonne(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);
        Utilisateur sauvegarderUtilisateur = utilisateurRepository.save(utilisateur);

        return utilisateurMapper.toDto(sauvegarderUtilisateur);
    }

    @Transactional
    public void supprimerParId(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable avec l'id : " + id);
        }
        this.utilisateurRepository.deleteById(id);
    }

    public Optional<UtilisateurDto> rechercherParNom(String nom) {
        return utilisateurRepository.findByNomUtilisateurContainingIgnoreCase(nom)
                .stream()
                .findFirst()
                .map(utilisateurMapper::toDto); // Conversion directe via le mapper
    }

    public Optional<UtilisateurDto> findById(Long id) {
        return utilisateurRepository.findById(id)
                .stream()
                .findFirst()
                .map(utilisateurMapper::toDto);
    }

    @Transactional
    public UtilisateurDto modifierUtilsateur(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);
        Utilisateur modifierUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(modifierUtilisateur);
    }

}
