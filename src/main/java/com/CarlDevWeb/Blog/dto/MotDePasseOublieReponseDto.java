package com.CarlDevWeb.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MotDePasseOublieReponseDto {
    private boolean succes;
    private String message;
}
