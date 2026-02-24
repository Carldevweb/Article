package com.CarlDevWeb.Blog.repository;


import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Favori;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriRepository extends JpaRepository<Favori, Long> {

    Optional<Favori> findByUtilisateurAndArticle(Utilisateur utilisateur, Article article);

    List<Favori> findByUtilisateur(Utilisateur utilisateur);

    long countByArticleId(Long articleId);

}
