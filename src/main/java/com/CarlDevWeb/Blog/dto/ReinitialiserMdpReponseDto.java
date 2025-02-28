package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class ReinitialiserMdpReponseDto {

    private String message;
    private boolean success;
    private String token;

    public ReinitialiserMdpReponseDto() {
    }

    public ReinitialiserMdpReponseDto(boolean success, String message) {
        this.message = message;
        this.success= success;
        this.token = token;
    }
}
