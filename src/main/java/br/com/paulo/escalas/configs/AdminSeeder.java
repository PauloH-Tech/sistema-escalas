package br.com.paulo.escalas.configs;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:}")
    private String email;

    @Value("${app.admin.senha:}")
    private String senha;

    @Override
    public void run(ApplicationArguments args) {
        if (email.isBlank() || senha.isBlank()) {
            log.warn("ADMIN_EMAIL/ADMIN_SENHA não definidos — admin inicial não será criado");
            return;
        }
        if (usuarioRepository.existsByEmail(email)) return;
        Usuario usuario = new Usuario();
        usuario.setNome("Administrador");
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(senha));
        usuario.setRole(UserRole.ADMIN);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);

        log.warn("Admin inicial criado: {} — troque a senha!", email);

    }
}
