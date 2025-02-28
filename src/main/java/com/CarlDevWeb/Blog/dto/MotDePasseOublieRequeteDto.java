package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class MotDePasseOublieRequeteDto {

    private String tokenTemporaire;
    private String nouveauMotDePasse;

}
