package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    List<Commentaire> findByArticleId(Long articleId);
}
