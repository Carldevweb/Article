package com.CarlDevWeb.Blog;

import com.CarlDevWeb.Blog.entity.Utilisateur;
import com.CarlDevWeb.Blog.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilisateurTest {


    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Test
    public void findByNom() {
        Utilisateur utilisateur1 = new Utilisateur();

        utilisateur1.setNomUtilisateur("Aragorn");

        utilisateurRepository.save(utilisateur1);
        utilisateurRepository.findByNomUtilisateurContainingIgnoreCase(utilisateur1.getNomUtilisateur());

        System.out.println(utilisateur1);

    }

}
