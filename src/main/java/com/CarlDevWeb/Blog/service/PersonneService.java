package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.model.Personne;
import com.CarlDevWeb.Blog.repository.PersonneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    @Transactional
    public Personne save (Personne personne) {
        return personneRepository.save(personne);
    }

    public void supprimerParId(Long id) {
        this.personneRepository.deleteById(id);
    }

    public List<Personne> rechercherParNom(String nom){
        return personneRepository.findByNomPersonneContainingIgnoreCase(nom);
    }

    public Optional<Personne> findById(Long id) {
        return personneRepository.findById(id);
    }

}
