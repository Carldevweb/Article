package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.UtilisateurDto;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UtilisateurDto toDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurDto utilisateurDto = new UtilisateurDto();

        utilisateurDto.setId(utilisateur.getId());
        utilisateurDto.setNomUtilisateur(utilisateur.getNomUtilisateur());
        utilisateurDto.setPrenomUtilisateur(utilisateur.getPrenomUtilisateur());
        utilisateurDto.setEmail(utilisateur.getEmail());

        return utilisateurDto;
    }

    public Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            return null;
        }

        Utilisateur utilisateurEntity = new Utilisateur();

        utilisateurEntity.setId(utilisateurDto.getId());
        utilisateurEntity.setPrenomUtilisateur(utilisateurDto.getPrenomUtilisateur());
        utilisateurEntity.setNomUtilisateur(utilisateurDto.getNomUtilisateur());
        utilisateurEntity.setEmail(utilisateurDto.getEmail());

        return utilisateurEntity;
    }
}
