package com.CarlDevWeb.Blog.service;

import com.CarlDevWeb.Blog.dto.PersonneDto;
import com.CarlDevWeb.Blog.mapper.PersonneMapper;
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

    @Autowired
    private PersonneMapper personneMapper;

    @Transactional
    public PersonneDto creerPersonne(PersonneDto personneDto) {
        Personne personne = personneMapper.toEntity(personneDto);
        Personne sauvegarderPersonne = personneRepository.save(personne);

        return personneMapper.toDto(sauvegarderPersonne);
    }

    public void supprimerParId(Long id) {
        if(!personneRepository.existsById(id)){
            throw new RuntimeException("Personne introuvable avec l'id : " + id);
        }
        this.personneRepository.deleteById(id);
    }

    public List<PersonneDto> rechercherParNom(String nom) {
        List<Personne> personnes = personneRepository.findByNomPersonneContainingIgnoreCase(nom);
        return personnes.stream()
                .map(personneMapper::toDto)
                .toList();
    }

    public Optional<Personne> findById(Long id) {
        return personneRepository.findById(id);
    }

}
