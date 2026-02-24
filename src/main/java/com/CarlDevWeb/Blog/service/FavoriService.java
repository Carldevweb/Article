package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Favori;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.mapper.FavoriMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.FavoriRepository;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


@Service

public class FavoriService {

    @Autowired
    FavoriRepository favoriRepository;
    @Autowired
    FavoriMapper favoriMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Transactional
    public FavoriDto ajouterFavori(Long articleId, String email) {

        if (articleId == null) {
            throw new IllegalArgumentException("articleId est obligatoire");
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("L'article n'existe pas"));

        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur inconnu"));

        favoriRepository.findByUtilisateurAndArticle(utilisateur, article)
                .ifPresent(f -> {
                    throw new IllegalArgumentException("Ce favori existe déjà");
                });

        Favori favori = new Favori();
        favori.setArticle(article);
        favori.setUtilisateur(utilisateur);
        favori.setDateDeCreation(LocalDateTime.now());

        return favoriMapper.toDto(favoriRepository.save(favori));
    }

    @Transactional(readOnly = true)
    public List<FavoriDto> findAllByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur inconnu"));

        return favoriRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(favoriMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FavoriDto> findAll() {
        return favoriRepository.findAll()
                .stream()
                .map(favoriMapper::toDto)
                .toList();
    }

    @Transactional
    public void supprimerParId(Long id, String email) {
        if (id == null) throw new IllegalArgumentException("id est obligatoire");

        Utilisateur utilisateur = utilisateurRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur inconnu"));

        Favori favori = favoriRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favori introuvable"));

        if (!favori.getUtilisateur().getId().equals(utilisateur.getId())) {
            throw new IllegalArgumentException("Accès interdit");
        }

        favoriRepository.delete(favori);
    }
}
