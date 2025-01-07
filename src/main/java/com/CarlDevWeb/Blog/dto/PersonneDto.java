package com.CarlDevWeb.Blog.dto;

public class PersonneDto {

    private String nomPersonne;
    private String prenomPersonne;

    public PersonneDto(String nomPersonne, String prenomPersonne) {
        this.nomPersonne = nomPersonne;
        this.prenomPersonne = prenomPersonne;
    }

    public PersonneDto() {
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
}
