package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.entity.Categorie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategorieMapper {

    public List<CategorieDto> listeCategoriedto(List<Categorie> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Categorie> listCategorieEntity(List<CategorieDto> categorieDtos) {
        if (categorieDtos == null) {
            return null;
        }
        return categorieDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public CategorieDto toDto(Categorie categorie) {
        if (categorie == null) {
            return null;
        }

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(categorie.getId());
        categorieDto.setNomCategorie(categorie.getNomCategorie());
        categorieDto.setImageUrl(categorie.getImageUrl());

        // Tu ne remplis pas articles ici (vu que tu gères via endpoint /{id}/articles)
        return categorieDto;
    }

    public Categorie toEntity(CategorieDto categorieDto) {
        if (categorieDto == null) {
            return null;
        }

        Categorie categorieEntity = new Categorie();
        categorieEntity.setId(categorieDto.getId());
        categorieEntity.setNomCategorie(categorieDto.getNomCategorie());
        categorieEntity.setImageUrl(categorieDto.getImageUrl());

        return categorieEntity;
    }
}