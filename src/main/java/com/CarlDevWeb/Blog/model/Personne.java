package com.CarlDevWeb.Blog.model;

import com.CarlDevWeb.Blog.enumerate.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomPersonne;
    private String prenomPersonne;
    private String motDePasse;
    private String email;

    private Role roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public void setNomPersonne(String nomPersonne) {
        this.nomPersonne = nomPersonne;
    }

    public String getPrenomPersonne() {
        return prenomPersonne;
    }

    public void setPrenomPersonne(String prenomPersonne) {
        this.prenomPersonne = prenomPersonne;
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
        return "Personne{" +
                "id=" + id +
                ", nomPersonne='" + nomPersonne + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
