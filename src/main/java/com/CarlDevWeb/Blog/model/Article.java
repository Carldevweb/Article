package com.CarlDevWeb.Blog.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private Date miseAJour;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires = new ArrayList<>();

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public void ajouterCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
        commentaire.setArticle(this);
    }

    public void retirerCommentaire(Commentaire commentaire){
        commentaires.remove(commentaire);
        commentaire.setArticle(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMiseAJour() {
        return miseAJour;
    }

    public void setMiseAJour(Date miseAJour) {
        this.miseAJour = miseAJour;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
