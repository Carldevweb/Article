package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.CommentaireDto;
import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Commentaire;
import com.CarlDevWeb.Blog.mapper.CommentaireMapper;
import com.CarlDevWeb.Blog.repository.ArticleRepository;
import com.CarlDevWeb.Blog.repository.CommentaireRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    CommentaireRepository commentaireRepository;

    @Autowired
    CommentaireMapper commentaireMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Transactional
    public CommentaireDto creerCommentaire(CommentaireDto commentaireDto) {
        Article article = articleRepository.findById(commentaireDto.getArticle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Artcile non trouvé"));

        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(commentaireDto.getContenu());

        if (commentaireDto.getAuteur() != null) {
            commentaire.setAuteur(commentaireDto.getAuteur());
        } else {
            commentaire.setAuteur("Auteur inconnu");
        }

        if (commentaireDto.getDateCreation() == null) {
            commentaire.setDateCreation(LocalDateTime.now());
        } else {
            commentaire.setDateCreation(commentaireDto.getDateCreation());
        }

        commentaire.setArticle(article);

        Commentaire commentaireEnregistre = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(commentaireEnregistre);
    }

    public void supprimerParId(Long id) {
        this.commentaireRepository.deleteById(id);
    }

    public Optional<CommentaireDto> findById(Long id) {
        return commentaireRepository.findById(id)
                .stream()
                .findFirst()
                .map(commentaireMapper::toDto);
    }

    public Optional<CommentaireDto> getCommentsByArticle(Long articleId) {
        return commentaireRepository.findByArticleId(articleId)
                .stream()
                .findFirst()
                .map(commentaireMapper::toDto);
    }
}
