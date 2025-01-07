package com.CarlDevWeb.Blog.mapper;

import com.CarlDevWeb.Blog.dto.PersonneDto;
import com.CarlDevWeb.Blog.model.Personne;
import org.springframework.stereotype.Component;

@Component
public class PersonneMapper {

    public PersonneDto toDto(Personne personne) {
        if (personne == null) {
            return null;
        }

        PersonneDto personneDto = new PersonneDto();
        personneDto.setNomPersonne(personne.getNomPersonne());
        personneDto.setPrenomPersonne(personne.getPrenomPersonne());

        return personneDto;
    }

    public Personne toEntity(PersonneDto personneDto) {
        if (personneDto == null) {
            return null;
        }

        Personne personneEntity = new Personne();

        personneEntity.setPrenomPersonne(personneDto.getPrenomPersonne());
        personneEntity.setNomPersonne(personneDto.getNomPersonne());

        return personneEntity;
    }
}
