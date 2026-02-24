package com.CarlDevWeb.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReinitialiserMdpReponseDto {

    private boolean success;
    private String message;
}
