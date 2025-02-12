package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Favori;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.mapper.FavoriMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.FavoriRepository;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public FavoriDto ajouterFavori(FavoriDto favoriDto) {
        Article article = articleRepository.findById(favoriDto.getArticle().getId())
                .orElseThrow(() -> new IllegalArgumentException("L'article n'existe pas"));

        Utilisateur utilisateur = utilisateurRepository.findById(favoriDto.getUtilisateur().getId())
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur n'existe pas"));

        Optional<Favori> favoriExistant = favoriRepository.findByUtilisateurAndArticle(utilisateur, article);
        if (favoriExistant.isPresent()) {
            throw new IllegalArgumentException("Ce favori existe déjà");
        }

        Favori favori = new Favori();

        favori.setDateDeCreation(favoriDto.getDateDeCreation());
        favori.setArticle(article);
        favori.setUtilisateur(utilisateur);

        Favori favoriSauvegarde = favoriRepository.save(favori);

        return favoriMapper.toDto(favoriSauvegarde);
    }

    public void supprimerParId(Long id) {
        this.favoriRepository.deleteById(id);
    }

}
