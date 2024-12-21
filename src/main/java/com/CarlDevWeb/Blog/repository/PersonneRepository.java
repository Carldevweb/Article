package com.CarlDevWeb.Blog.repository;

import com.CarlDevWeb.Blog.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    List<Personne> findByNomPersonneContainingIgnoreCase(String nomPersonne);
}
