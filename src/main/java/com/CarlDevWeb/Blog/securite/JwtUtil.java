package com.CarlDevWeb.Blog.securite;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Injecte la clé secrète depuis application.properties
    private String secret;
    private Key signingKey;

    @PostConstruct
    public void init() {
        if (secret == null) {
            throw new IllegalArgumentException("La clé secrète JWT ne peut pas être vide");
        }

        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            signingKey = Keys.hmacShaKeyFor(keyBytes);
            System.out.println("✅ Clé après décodage (Base64) : " + Base64.getEncoder().encodeToString(keyBytes));


            System.out.println("Clé secrète correctement chargée");
        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur lors du décodage de la clé secrète JWT", e);
        }

    }

    private Key getSigningKey() {
        return signingKey;
    }

    public String generateToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername());
    }

    /** Génère un token à partir d'un simple identifiant (email) */
    public String generateToken(String subject) {
        return buildToken(subject);
    }
    private String buildToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 heure
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'extraction des claims du token : " + e.getMessage());
            throw e;
        }
    }

    public boolean estTokenExpire(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification du token expiré : " + e.getMessage());
            return true;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        final Date expiration = extractExpiration(token);

        if (expiration.before(new Date())) {
            System.out.println("⛔ Le token a expiré !");
            return false;
        }

        return username.equals(userDetails.getUsername());
    }

    public String renouvelerToken(String ancienToken) {
        String tokenSansPrefixe = ancienToken.replace("Bearer ", "");
        String email = extractEmail(tokenSansPrefixe);
        return generateToken(email);
    }
}
