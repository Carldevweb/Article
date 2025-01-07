package com.CarlDevWeb.Blog;

import com.CarlDevWeb.Blog.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonneTest {


    @Autowired
    PersonneRepository personneRepository;

//    @Test
//    public void findByNom(){
//        Personne personne1 = new Personne();
//
//        personne1.setNomPersonne("Aragorn");
//
//        personneRepository.save(personne1);
//        personneRepository.findByNomPersonneContainingIgnoreCase(personne1.getNomPersonne());
//
//        System.out.println(personne1);
//
//    }

}
