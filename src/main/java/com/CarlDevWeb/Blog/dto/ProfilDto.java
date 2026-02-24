package com.CarlDevWeb.Blog.dto;


import com.CarlDevWeb.Blog.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilDto {
    private Long id;
    private String email;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private Role role;
}
