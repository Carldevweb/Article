package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByNomUtilisateurContainingIgnoreCase(String nomUtilisateur);

    Optional<Utilisateur> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

    Optional<Utilisateur> findByToken(String token);
}
