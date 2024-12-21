package com.CarlDevWeb.Blog.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", dateCreation=" + dateCreation +
                ", article=" + article +
                '}';
    }
}
