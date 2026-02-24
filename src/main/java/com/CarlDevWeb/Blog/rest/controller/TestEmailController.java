package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-email")
public class TestEmailController {

    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<String> envoyerEmailTest(
            @RequestParam(defaultValue = "test@example.com") String email
    ) {

        String fakeToken = "TEST_TOKEN_123";

        emailService.envoyerResetPassword(email, fakeToken);

        return ResponseEntity.ok(
                "Simulation OK. Vérifie la console : lien reset imprimé."
        );
    }
}
