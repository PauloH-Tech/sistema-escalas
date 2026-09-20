package br.com.paulo.escalas.configs.security;

import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String PREFIXO = "Bearer ";

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(PREFIXO) || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(PREFIXO.length());
        Optional<Claims> claims = tokenService.validar(token);

        if (claims.isPresent()) {
            Optional<Usuario> encontrado = buscarPorId(claims.get().getSubject());
            if (encontrado.isPresent() && encontrado.get().isAtivo()) {
                Usuario usuario = encontrado.get();

                var authToken = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<Usuario> buscarPorId(String subject) {
        try {
            return usuarioRepository.findById(UUID.fromString(subject));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
