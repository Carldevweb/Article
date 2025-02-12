package com.CarlDevWeb.Blog.repository;


import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Favori;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriRepository extends JpaRepository<Favori, Long> {

    Optional<Favori> findByUtilisateurAndArticle(Utilisateur utilisateur, Article article);
}
