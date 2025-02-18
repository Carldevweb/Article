package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class AuthentificationReponseDto {

    private String token;

    public AuthentificationReponseDto(String token) {
        this.token = token;
    }
}
