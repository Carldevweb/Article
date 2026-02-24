package com.CarlDevWeb.Blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "articles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nomCategorie;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    @Builder.Default
    private Set<Article> articles = new LinkedHashSet<>();
}