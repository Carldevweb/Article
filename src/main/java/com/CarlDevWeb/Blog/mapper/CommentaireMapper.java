package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.CommentaireDto;
import com.CarlDevWeb.Blog.model.Commentaire;
import org.springframework.stereotype.Component;

@Component
public class CommentaireMapper {

    public CommentaireDto toDto(Commentaire commentaire) {
        if (commentaire == null) {
            return null;
        }

        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setContenu(commentaire.getContenu());
        commentaireDto.setAuteur(commentaire.getAuteur());

        return commentaireDto;
    }

    public Commentaire toEntity(CommentaireDto commentaireDto) {
        if (commentaireDto == null) {
            return null;
        }

        Commentaire commentaireEntity = new Commentaire();
        commentaireEntity.setContenu(commentaireDto.getContenu());
        commentaireEntity.setAuteur(commentaireDto.getAuteur());

        return commentaireEntity;
    }
}
