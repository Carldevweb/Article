package com.CarlDevWeb.Blog.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class ArticleDto {

    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private Date miseAJour;

    public ArticleDto(Long id, String titre, String contenu, String auteur, LocalDateTime dateCreation, Date miseAJour) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
        this.miseAJour = miseAJour;
    }

    public ArticleDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public Date getMiseAJour() {
        return miseAJour;
    }

    public void setMiseAJour(Date miseAJour) {
        this.miseAJour = miseAJour;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", dateCreation=" + dateCreation +
                ", miseAJour=" + miseAJour +
                '}';
    }
}
