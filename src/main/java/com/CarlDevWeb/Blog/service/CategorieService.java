package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.mapper.CategorieMapper;
import com.CarlDevWeb.Blog.repository.CategorieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    CategorieMapper categorieMapper;

    @Transactional
    public CategorieDto creerCategorie(CategorieDto categorieDto) {
        Categorie categorie = categorieMapper.toEntity(categorieDto);

        // Ici, tu peux choisir de ne pas lier de catégorie aux articles si tu ne veux pas qu'elle en ait
        // Si l'utilisateur ne fournit pas de catégorie associée à un article, tu peux laisser une liste vide
        if (categorieDto.getArticles() == null || categorieDto.getArticles().isEmpty()) {
            categorie.setArticles(new ArrayList<>());
        }

        // Sauvegarder la catégorie sans articles associés si nécessaire
        Categorie savedCategorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(savedCategorie);
    }

    @Transactional
    public List<CategorieDto> listeCategorie() {
        List<Categorie> listeArticles = categorieRepository.findAll();
        return listeArticles.stream()
                .map(categorieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategorieDto getParId(Long id) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        return categorie != null ? categorieMapper.toDto(categorie) : null;
    }


    public void supprimerParId(Long id) {
        this.categorieRepository.deleteById(id);
    }
}
