package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class ReinitialiserMdpRequeteDto {

    private String token;
    private String nouveauMotDePasse;

}
