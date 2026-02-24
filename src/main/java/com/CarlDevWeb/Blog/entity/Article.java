package com.CarlDevWeb.Blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"commentaires", "favori", "media", "categories"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String auteur;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreation;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date miseAJour;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private Set<Commentaire> commentaires = new LinkedHashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("article")
    @Builder.Default
    private Set<Favori> favori = new LinkedHashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @JsonManagedReference
    @Builder.Default
    private Set<Media> media = new LinkedHashSet<>();


    @ManyToMany
    @JoinTable(
            name = "article_categorie",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Categorie> categories = new LinkedHashSet<>();

    // Helpers
    public void ajouterCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
        commentaire.setArticle(this);
    }

    public void retirerCommentaire(Commentaire commentaire) {
        commentaires.remove(commentaire);
        commentaire.setArticle(null);
    }

    public void ajouterMedia(Media m) {
        media.add(m);
        m.setArticle(this);
    }

    public void retirerMedia(Media m) {
        media.remove(m);
        m.setArticle(null);
    }

    public void ajouterCategorie(Categorie c) {
        categories.add(c);
    }

    public void retirerCategorie(Categorie c) {
        categories.remove(c);
    }
}
