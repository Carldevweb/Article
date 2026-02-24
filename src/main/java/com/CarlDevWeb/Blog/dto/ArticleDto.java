package com.CarlDevWeb.Blog.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArticleDto {

    private Long id;
    private String titre;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;
    private Date miseAJour;

    private List<Long> categoriesIds;
    private int favoriNb;

    // AJOUT : médias associés (pour l’image)
    private List<MediaDto> medias;

    private String mainImageUrl;

}
