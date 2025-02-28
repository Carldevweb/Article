package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class ReinitialiserEmailReponseDto {

    private String email;
    private boolean success;

    public ReinitialiserEmailReponseDto() {
    }

    public ReinitialiserEmailReponseDto(String email, boolean success) {
        this.email = email;
        this.success = success;
    }
}
