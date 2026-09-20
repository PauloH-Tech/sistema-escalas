package br.com.paulo.escalas.services;

import br.com.paulo.escalas.configs.security.TokenService;
import br.com.paulo.escalas.entities.usuarios.PasswordResetToken;
import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.LoginRequestDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.RegisterDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.ResetPasswordDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.TokenResponseDTO;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.repositories.PasswordResetTokenRepository;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final int VALIDADE_RESET_MINUTOS = 15;

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );
        Usuario usuario = (Usuario) auth.getPrincipal();
        return TokenResponseDTO.de(tokenService.gerarToken(usuario), usuario);
    }

    @Transactional
    public void solicitarResetSenha(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            String tokenPuro = gerarTokenAleatorio();

            PasswordResetToken reset = new PasswordResetToken();
            reset.setUsuario(usuario);
            reset.setTokenHash(sha256(tokenPuro));
            reset.setExpiracao(LocalDateTime.now().plusMinutes(VALIDADE_RESET_MINUTOS));

            // TODO: enviar por e-mail (JavaMailSender). Por enquanto, log.
            log.info("Token de reset para {}: {}", email, tokenPuro);
        });
    }

    @Transactional
    public void resetarSenha(ResetPasswordDTO dto) {
        PasswordResetToken reset = resetTokenRepository
                .findByTokenHash(sha256(dto.token()))
                .filter(PasswordResetToken::isValido)
                .orElseThrow(() -> new RegraDeNegocioException("Token inválido ou expirado"));
        Usuario usuario = reset.getUsuario();
        usuario.setPassword(passwordEncoder.encode(dto.novaSenha()));
        reset.setUsado(true);
    }

    private String gerarTokenAleatorio() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String valor) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(valor.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
