package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class ReinitialiserMdpRequeteDto {

    private String tokenTemporaire;
    private String nouveauMotDePasse;

    public String getToken() {
        return tokenTemporaire;
    }
}
