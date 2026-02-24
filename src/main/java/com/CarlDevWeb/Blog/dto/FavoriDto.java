package com.CarlDevWeb.Blog.dto;

import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriDto {

    private Long id;
    private Long utilisateurId;
    private Long articleId;
    private LocalDateTime dateDeCreation;

}
