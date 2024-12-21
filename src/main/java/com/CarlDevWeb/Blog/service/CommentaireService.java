package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dao.CommentaireDao;
import com.CarlDevWeb.Blog.model.Commentaire;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentaireService {

    @Autowired
    CommentaireDao commentaireDao;

    @Transactional
    public Commentaire save(Commentaire commentaire) {
        commentaire.setDateCreation(LocalDateTime.now());
        return commentaireDao.save(commentaire);
    }

    public void deleteById(Long id){
        this.commentaireDao.deleteById(id);
    }


}
