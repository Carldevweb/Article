package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.AdminUserDto;
import com.CarlDevWeb.Blog.dto.CreateCategorieRequest;
import com.CarlDevWeb.Blog.dto.UpdateRoleRequest;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.repository.CategorieRepository;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UtilisateurRepository utilisateurRepository;
    private final CategorieRepository categorieRepository;

    public Page<AdminUserDto> getUsers(String q, Pageable pageable) {

        Page<Utilisateur> page;

        if (q != null && !q.isBlank()) {
            page = utilisateurRepository
                    .findByEmailContainingIgnoreCaseOrNomUtilisateurContainingIgnoreCase(
                            q, q, pageable);
        } else {
            page = utilisateurRepository.findAll(pageable);
        }

        return page.map(this::toDto);
    }

    @Transactional
    public AdminUserDto updateRole(Long id, UpdateRoleRequest request) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setRole(request.getRole());
        return toDto(utilisateurRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        utilisateurRepository.deleteById(id);
    }

    @Transactional
    public Categorie createCategorie(CreateCategorieRequest request) {

        if (categorieRepository.existsByNomCategorieIgnoreCase(request.getNomCategorie())) {
            throw new RuntimeException("La catégorie existe déjà");
        }

        Categorie categorie = new Categorie();
        categorie.setNomCategorie(request.getNomCategorie());

        return categorieRepository.save(categorie);
    }

    private AdminUserDto toDto(Utilisateur user) {
        AdminUserDto dto = new AdminUserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNomUtilisateur(user.getNomUtilisateur());
        dto.setPrenomUtilisateur(user.getPrenomUtilisateur());
        dto.setRole(user.getRole());
        return dto;
    }
}
