package com.CarlDevWeb.Blog.dto;

import lombok.Data;

@Data
public class ReinitialiserEmailRequeteDto {

    private String email;
    private String tokenTemporaire;

    public ReinitialiserEmailRequeteDto() {
    }

    public ReinitialiserEmailRequeteDto(String email, String tokenTemporaire) {
        this.email = email;
        this.tokenTemporaire = tokenTemporaire;
    }
}
