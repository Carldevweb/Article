package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Optional<Media> findFirstByArticleIdOrderByIdAsc(Long articleId);

    List<Media> findByArticleIdOrderByIdAsc(Long articleId);
}
