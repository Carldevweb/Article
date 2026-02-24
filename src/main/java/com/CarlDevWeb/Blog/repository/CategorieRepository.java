package com.CarlDevWeb.Blog.repository;


import com.CarlDevWeb.Blog.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findByNomCategorieIn(List<String> nomsCategorie);

    boolean existsByNomCategorieIgnoreCase(String nomCategorie);
}
