package com.CarlDevWeb.Blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InscriptionRequeteDto {

    @NotBlank
    private String nomUtilisateur;

    @NotBlank
    private String prenomUtilisateur;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String motDePasse;
}
