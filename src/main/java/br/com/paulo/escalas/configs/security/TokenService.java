package br.com.paulo.escalas.configs.security;

import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final SecretKey key;
    private final long expiracaoMs;

    public TokenService(
            @Value("${api.security.token.secret}") String secret,
            @Value("${api.security.token.expiration-ms}") long expiracaoMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMs = expiracaoMs;
    }


    public String gerarToken(Usuario usuario) {
        Instant agora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getId().toString())
                .claim("email", usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusMillis(expiracaoMs)))
                .signWith(key)
                .compact();
    }

    public Optional<Claims> validar(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }



}
