package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.entity.Favori;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FavoriMapper {

    public FavoriDto toDto(Favori favori) {
        if (favori == null) return null;

        FavoriDto dto = new FavoriDto();
        dto.setId(favori.getId());
        dto.setUtilisateurId(favori.getUtilisateur().getId());
        dto.setArticleId(favori.getArticle().getId());
        dto.setDateDeCreation(favori.getDateDeCreation());

        return dto;
    }
}
