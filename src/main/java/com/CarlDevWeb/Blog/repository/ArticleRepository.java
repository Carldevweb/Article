package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.Article;
import com.CarlDevWeb.Blog.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategories_Id(Long categorieId);

    List<Article> findByTitreContainingIgnoreCase(String titre);

    List<Article> findByCategoriesIn(List<Categorie> categories);

    @Query("""
            SELECT a
            FROM Article a
            LEFT JOIN FETCH a.categories
            WHERE a.id = :id
           """)
    Optional<Article> findByIdWithCategories(@Param("id") Long id);

    @Query("""
            SELECT DISTINCT a
            FROM Article a
            LEFT JOIN FETCH a.categories
           """)
    List<Article> findAllWithCategories();

    @Query("""
            select a.id, count(f)
            from Article a
            left join a.favori f
            group by a.id
           """)
    List<Object[]> countFavoriByArticleId();

    @Query("""
            select distinct a
            from Article a
            left join fetch a.categories c
            where c.id = :categorieId
           """)
    List<Article> findByCategorieIdWithCategories(@Param("categorieId") Long categorieId);

    // ✅ IMPORTANT : DISTINCT pour éviter doublons (ManyToMany + OneToMany)
    @Query("""
           select distinct a
           from Article a
           left join fetch a.media m
           left join fetch a.categories c
           where a.id = :id
           """)
    Optional<Article> findByIdWithMediaAndCategories(@Param("id") Long id);
}
