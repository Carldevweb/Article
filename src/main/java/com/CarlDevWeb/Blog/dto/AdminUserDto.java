package com.CarlDevWeb.Blog.dto;

import com.CarlDevWeb.Blog.enums.Role;
import lombok.Data;

@Data
public class AdminUserDto {

    private Long id;
    private String email;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private Role role;
}
