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
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 heure
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final Date expiration = extractExpiration(token);

        if (expiration.before(new Date())) {
            System.out.println("⛔ Le token a expiré !");
            return false;
        }

        return username.equals(userDetails.getUsername());
    }

}
