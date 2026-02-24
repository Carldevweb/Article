package com.CarlDevWeb.Blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorieDto {

    private Long id;
    private String nomCategorie;

    private String imageUrl;

    private List<ArticleDto> articles;

}