package com.CarlDevWeb.Blog.dto;

import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Utilisateur;

import java.time.LocalDateTime;

public class FavoriDto {

    private Long id;
    private Utilisateur utilisateur;
    private Article article;
    private LocalDateTime dateDeCreation;

    public FavoriDto(Long id, Utilisateur utilisateur, Article article, LocalDateTime dateDeCreation) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.article = article;
        this.dateDeCreation = dateDeCreation;
    }

    public FavoriDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public LocalDateTime getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDateTime dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    @Override
    public String toString() {
        return "FavoriDto{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", article=" + article +
                ", dateDeCreation=" + dateDeCreation +
                '}';
    }
}
