package com.CarlDevWeb.Blog.entity;

import com.CarlDevWeb.Blog.enumerate.Role;
import jakarta.persistence.*;

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

    private Role roles;

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
