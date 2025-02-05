package com.CarlDevWeb.Blog.entity;

import com.CarlDevWeb.Blog.enumerate.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String motDePasse;
    private String email;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Favori> favori = new ArrayList<>();

    public List<Favori> getFavori() {
        return favori;
    }

    public void setFavori(List<Favori> favori) {
        this.favori = favori;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return roles;
    }

    public void setRole(Role roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
