package com.CarlDevWeb.Blog.dto;

import java.time.LocalDateTime;

public class CommentaireDto {

    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;

    public CommentaireDto(String contenu, String auteur, LocalDateTime dateCreation) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
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
}
