package com.CarlDevWeb.Blog.dto;

import com.CarlDevWeb.Blog.entity.Article;

import java.time.LocalDateTime;

public class CommentaireDto {

    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private Article article;

    public CommentaireDto(String contenu, String auteur, LocalDateTime dateCreation, Article article) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public CommentaireDto() {
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "CommentaireDto{" +
                "contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", dateCreation=" + dateCreation +
                ", article=" + article +
                '}';
    }
}
