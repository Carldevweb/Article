package com.CarlDevWeb.Blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.frontend.base-url:http://localhost:4200}")
    private String frontendBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerResetPassword(String email, String token) {
        String resetLink = frontendBaseUrl + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Réinitialisation du mot de passe");
        message.setText(
                "Bonjour,\n\n" +
                        "Clique sur ce lien pour réinitialiser ton mot de passe :\n" +
                        resetLink + "\n\n" +
                        "Si tu n'es pas à l'origine de cette demande, ignore ce message.\n"
        );

        System.out.println("[MAIL] envoyerResetPassword - Tentative d'envoi");
        System.out.println("[MAIL] From=" + from + " To=" + email);
        System.out.println("[MAIL] Link=" + resetLink);

        try {
            mailSender.send(message);
            System.out.println("[MAIL] envoyerResetPassword - Envoyé OK");
        } catch (Exception e) {
            System.out.println("[MAIL] envoyerResetPassword - ECHEC envoi : " + e.getClass().getName() + " - " + e.getMessage());
            throw e; // important: on remonte l'erreur pour la voir côté API/logs
        }
    }

    public void envoyerEmailTest(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Test SMTP Spring Boot");
        message.setText("Si tu lis ce message, l'envoi SMTP fonctionne.");

        System.out.println("[MAIL] envoyerEmailTest - Tentative d'envoi");
        System.out.println("[MAIL] From=" + from + " To=" + email);

        try {
            mailSender.send(message);
            System.out.println("[MAIL] envoyerEmailTest - Envoyé OK");
        } catch (Exception e) {
            System.out.println("[MAIL] envoyerEmailTest - ECHEC envoi : " + e.getClass().getName() + " - " + e.getMessage());
            throw e;
        }
    }
}
