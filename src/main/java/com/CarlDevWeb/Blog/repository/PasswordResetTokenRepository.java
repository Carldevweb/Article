package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.PasswordResetToken;
import com.CarlDevWeb.Blog.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findFirstByUtilisateurAndUsedAtIsNullOrderByExpiresAtDesc(Utilisateur utilisateur);

    long deleteByExpiresAtBefore(LocalDateTime dateTime);
}
