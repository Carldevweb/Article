package com.CarlDevWeb.Blog.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class ArticleDto {

    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private Date miseAJour;

    public ArticleDto(String titre, String contenu, String auteur, LocalDateTime dateCreation, Date miseAJour) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
        this.miseAJour = miseAJour;
    }

    public ArticleDto() {
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
}
