package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByNomUtilisateurContainingIgnoreCase(String nomUtilisateur);

    Optional<Utilisateur> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    long countByRole(Role role);

    Page<Utilisateur> findByEmailContainingIgnoreCaseOrNomUtilisateurContainingIgnoreCase(
            String email,
            String nomUtilisateur,
            Pageable pageable
    );
}
