package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class InscriptionRequeteDto {

    private Long id;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private String email;
    private String motDePasse;

}
