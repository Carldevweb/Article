package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.FavoriDto;
import com.CarlDevWeb.Blog.entity.Favori;
import org.springframework.stereotype.Component;

@Component
public class FavoriMapper {

    public FavoriDto toDto(Favori favori) {
        if (favori == null) {
            return null;
        }

        FavoriDto favoriDto = new FavoriDto();
        favoriDto.setId(favori.getId());
        favoriDto.setArticle(favori.getArticle());
        favoriDto.setUtilisateur(favoriDto.getUtilisateur());
        favoriDto.setDateDeCreation(favoriDto.getDateDeCreation());

        return favoriDto;
    }

    public Favori toEntity(FavoriDto favoriDto) {
        if (favoriDto == null) {
            return null;
        }

        Favori favoriEntity = new Favori();
        favoriEntity.setId(favoriDto.getId());
        favoriEntity.setUtilisateur(favoriDto.getUtilisateur());
        favoriEntity.setArticle(favoriDto.getArticle());
        favoriEntity.setDateDeCreation(favoriEntity.getDateDeCreation());

        return favoriEntity;
    }
}
