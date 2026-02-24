package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.ProfilDto;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public ProfilDto toProfilDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return new ProfilDto(
                utilisateur.getId(),
                utilisateur.getEmail(),
                utilisateur.getNomUtilisateur(),
                utilisateur.getPrenomUtilisateur(),
                utilisateur.getRole()
        );
    }

    public void updateEntityFromProfilDto(Utilisateur utilisateur, ProfilDto dto) {
        if (utilisateur == null || dto == null) {
            return;
        }

        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setPrenomUtilisateur(dto.getPrenomUtilisateur());
        utilisateur.setEmail(dto.getEmail());
    }
}
