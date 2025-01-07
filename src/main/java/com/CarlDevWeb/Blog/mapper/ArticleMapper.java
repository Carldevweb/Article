package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.ArticleDto;
import com.CarlDevWeb.Blog.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public ArticleDto toDto(Article article) {
        if (article == null) {
            return null;
        }

        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitre(article.getTitre());
        articleDto.setContenu(article.getContenu());
        articleDto.setAuteur(articleDto.getAuteur());
        articleDto.setDateCreation(article.getDateCreation());
        articleDto.setMiseAJour(article.getMiseAJour());

        return articleDto;
    }

    public Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }
        Article articleEntity = new Article();
        articleEntity.setTitre(articleDto.getTitre());
        articleEntity.setContenu(articleDto.getContenu());
        articleEntity.setAuteur(articleEntity.getAuteur());
        articleEntity.setDateCreation(articleEntity.getDateCreation());
        articleEntity.setMiseAJour(articleEntity.getMiseAJour());

        return articleEntity;
    }
}
