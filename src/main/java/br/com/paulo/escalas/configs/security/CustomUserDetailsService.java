package br.com.paulo.escalas.configs.security;

import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name())
                .disabled(!usuario.isAtivo())
                .build();
    }
}
