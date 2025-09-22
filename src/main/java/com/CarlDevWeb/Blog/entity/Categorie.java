package com.CarlDevWeb.Blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomCategorie;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference // Permet de gérer la sérialisation inverse pour éviter les boucles infinies
    private List<Article> articles = new ArrayList<>();

}
