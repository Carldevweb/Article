package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class InscriptionReponseDto {
    private Long id;
    private String email;
    private String nomUtilisateur;
    private String role;
}
