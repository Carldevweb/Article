package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.dto.MediaDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.entity.Media;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ArticleMapper {

    // ========================
    // ENTITY -> DTO
    // ========================
    public ArticleDto toDto(Article article) {
        if (article == null) return null;

        ArticleDto dto = ArticleDto.builder()
                .id(article.getId())
                .titre(article.getTitre())
                .contenu(article.getContenu())
                .auteur(article.getAuteur())
                .dateCreation(article.getDateCreation())
                .miseAJour(article.getMiseAJour())
                .favoriNb(article.getFavori() != null ? article.getFavori().size() : 0)
                .build();

        if (article.getCategories() != null) {
            dto.setCategoriesIds(
                    article.getCategories()
                            .stream()
                            .map(Categorie::getId)
                            .toList()
            );
        }

        if (article.getMedia() != null) {
            dto.setMedias(
                    article.getMedia()
                            .stream()
                            .map(this::toMediaDto)
                            .toList()
            );
        } else {
            dto.setMedias(Collections.emptyList());
        }

        return dto;
    }

    private MediaDto toMediaDto(Media media) {
        if (media == null) return null;

        return MediaDto.builder()
                .id(media.getId())
                .url(media.getUrl())
                .type(media.getType())
                .articleId(media.getArticle() != null ? media.getArticle().getId() : null)
                .build();
    }

    // ========================
    // DTO -> ENTITY
    // ========================
    public Article toEntity(ArticleDto dto) {
        if (dto == null) return null;

        Article article = new Article();
        article.setTitre(dto.getTitre());
        article.setContenu(dto.getContenu());
        article.setAuteur(dto.getAuteur());
        article.setDateCreation(dto.getDateCreation());
        article.setMiseAJour(dto.getMiseAJour());

        // ⚠️ categories + medias sont gérées dans le service
        // pas ici, sinon tu vas casser tes relations

        return article;
    }
}
