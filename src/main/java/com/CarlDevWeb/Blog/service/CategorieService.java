package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.CategorieDto;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.mapper.CategorieMapper;
import com.CarlDevWeb.Blog.repository.CategorieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
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

        if (categorieDto.getArticles() == null || categorieDto.getArticles().isEmpty()) {
            categorie.setArticles(new LinkedHashSet<>());
        }

        Categorie savedCategorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(savedCategorie);
    }

    @Transactional
    public List<CategorieDto> listeCategorie() {
        List<Categorie> listeCategories = categorieRepository.findAll();
        return listeCategories.stream()
                .map(categorieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Categorie update(Long id, CategorieDto dto) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categorie non trouvée : " + id));

        categorie.setNomCategorie(dto.getNomCategorie());
        categorie.setImageUrl(dto.getImageUrl());

        return categorieRepository.save(categorie);
    }

    @Transactional
    public CategorieDto getParId(Long id) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        return categorie != null ? categorieMapper.toDto(categorie) : null;
    }

    @Transactional
    public CategorieDto updateImageUrl(Long id, String imageUrl) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie == null) {
            return null;
        }
        categorie.setImageUrl(imageUrl);
        Categorie saved = categorieRepository.save(categorie);
        return categorieMapper.toDto(saved);
    }

    public void supprimerParId(Long id) {
        this.categorieRepository.deleteById(id);
    }
}