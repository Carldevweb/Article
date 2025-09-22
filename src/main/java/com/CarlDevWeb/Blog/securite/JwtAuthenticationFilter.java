package com.CarlDevWeb.Blog.securite;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private static final Set<String> WHITELIST_PREFIXES = Set.of(
            "/authentification/",  // login, refresh, etc.
            "/error"               // page d'erreur
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UtilisateurDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    private boolean isWhitelisted(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;
        String path = request.getRequestURI();
        for (String prefix : WHITELIST_PREFIXES) {
            if (path.startsWith(prefix)) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        // Laisse passer sans toucher au token
        if (isWhitelisted(request)) {
            chain.doFilter(request, response);
            return;
        }

        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            try {
                String email = jwtUtil.extractEmail(token); // <-- maintenant DANS le try
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.warn("❌ Token invalide pour l'utilisateur: " + email);
                    }
                }
            } catch (ExpiredJwtException e) {
                // Token expiré : on n’authentifie pas, on laisse la requête continuer.
                // Si l’endpoint est protégé, il finira en 401 via Security.
                logger.warn("⏰ Token expiré", e);
            } catch (JwtException e) {
                // Token mal formé / signature invalide / etc.
                logger.warn("❌ Token JWT invalide", e);
            } catch (Exception e) {
                logger.error("❌ Erreur lors du traitement du token", e);
            }
        }

        chain.doFilter(request, response);
    }
}
