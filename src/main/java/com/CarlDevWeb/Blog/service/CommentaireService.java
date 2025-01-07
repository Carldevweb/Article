package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.CommentaireDto;
import com.CarlDevWeb.Blog.mapper.CommentaireMapper;
import com.CarlDevWeb.Blog.model.Commentaire;
import com.CarlDevWeb.Blog.repository.CommentaireRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    CommentaireRepository commentaireRepository;

    @Autowired
    CommentaireMapper commentaireMapper;

    @Transactional
    public CommentaireDto enrgistrerCommentaire(CommentaireDto commentaireDto) {
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDto);
        commentaireDto.setDateCreation(LocalDateTime.now());
        Commentaire sauvegarderCommentaire = commentaireRepository.save(commentaire);

        return commentaireMapper.toDto(sauvegarderCommentaire);
    }

    public void supprimerParId(Long id){
        this.commentaireRepository.deleteById(id);
    }

    public Optional<Commentaire> findById(Long id){
        return commentaireRepository.findById(id);
    }

    public List<Commentaire> getCommentsByArticle(Long articleId) {
        return commentaireRepository.findByArticleId(articleId);
    }
}
