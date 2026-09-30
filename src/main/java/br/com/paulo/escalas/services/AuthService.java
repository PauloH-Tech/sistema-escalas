package br.com.paulo.escalas.services;

import br.com.paulo.email.services.EmailOutboxService;
import br.com.paulo.email.services.EmailTemplateService;
import br.com.paulo.escalas.configs.security.TokenService;
import br.com.paulo.escalas.entities.usuarios.PasswordResetToken;
import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.LoginRequestDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.PrimeiroAcessoDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.ResetPasswordDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.TokenResponseDTO;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.repositories.PasswordResetTokenRepository;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EmailOutboxService emailOutboxService;
    private final EmailTemplateService emailTemplateService;


    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizarEmail(dto.email()), dto.senha())
        );
        Usuario usuario = (Usuario) auth.getPrincipal();
        return TokenResponseDTO.de(tokenService.gerarToken(usuario), usuario);
    }

    /**
     * Envia o código de primeiro acesso. Responde igual exista ou não o e-mail,
     * para não revelar quais e-mails estão cadastrados.
     */
    @Transactional
    public void solicitarPrimeiroAcesso(String email) {
        usuarioRepository.findByEmail(normalizarEmail(email))
                .filter(Usuario::isAtivo)
                .ifPresentOrElse(
                        usuario -> {
                            if (!usuario.isPrimeiroAcessoPendente()) {
                                throw new RegraDeNegocioException("E-mail já criado, tente redefinir a senha");
                            }
                            enviarCodigoPrimeiroAcesso(usuario);
                        },
                        () -> {
                            //TODO: talvez recebera essa mensagem se primeiro acesso for false
                            throw new RegraDeNegocioException("E-mail não cadastrado previamente. Entre em contato com o administrador.");
                        });
    }

    @Transactional
    public TokenResponseDTO confirmarPrimeiroAcesso(PrimeiroAcessoDTO dto) {
        PasswordResetToken codigo = consumirCodigo(dto.token());
        Usuario usuario = codigo.getUsuario();
        if (!usuario.isPrimeiroAcessoPendente()) {
            throw new RegraDeNegocioException("Primeiro acesso já concluído. Use 'Esqueci minha senha'.");
        }
        usuario.setNome(dto.nome().trim());
        usuario.setPassword(passwordEncoder.encode(dto.senha()));
        // já devolve o token para o app entrar direto, sem pedir login de novo
        return TokenResponseDTO.de(tokenService.gerarToken(usuario), usuario);
    }

    @Transactional
    public void solicitarResetSenha(String email) {
        usuarioRepository.findByEmail(normalizarEmail(email))
                .filter(Usuario::isAtivo)
                .ifPresentOrElse(usuario -> {
                    // quem ainda não fez o primeiro acesso recebe o código de primeiro acesso
                    if (usuario.isPrimeiroAcessoPendente()) {
                        throw new RegraDeNegocioException("Primeiro acesso não realizado, crie sua conta primeiro");
                    }
                    String tokenPuro = gerarCodigo(usuario);
                    emailOutboxService.salvarEmail(
                            usuario.getEmail(),
                            "REDEFINIÇÃO DE SENHA",
                            emailTemplateService.montarTemplateResetSenha(usuario.getNomeExibicao(), tokenPuro, VALIDADE_RESET_MINUTOS));
                    log.info("Reset de senha solicitado para {}, token: {}", email, tokenPuro);
                }, () -> {throw new RegraDeNegocioException("E-mail não cadastrado.");});
    }

    @Transactional
    public void resetarSenha(ResetPasswordDTO dto) {
        PasswordResetToken reset = consumirCodigo(dto.token());
        Usuario usuario = reset.getUsuario();
        if (usuario.isPrimeiroAcessoPendente()) {
            throw new RegraDeNegocioException("Conclua o primeiro acesso para definir sua senha");
        }
        usuario.setPassword(passwordEncoder.encode(dto.novaSenha()));
    }

    private void enviarCodigoPrimeiroAcesso(Usuario usuario) {
        String tokenPuro = gerarCodigo(usuario);
        emailOutboxService.salvarEmail(
                usuario.getEmail(),
                "PRIMEIRO ACESSO",
                emailTemplateService.montarTemplatePrimeiroAcesso(usuario.getNomeExibicao(), tokenPuro, VALIDADE_RESET_MINUTOS));
        log.info("Primeiro acesso solicitado para {}, token: {}", usuario.getEmail(), tokenPuro);
    }

    /**
     * Gera e persiste um código de uso único; retorna o valor puro (só o hash vai para o banco).
     */
    private String gerarCodigo(Usuario usuario) {
        String tokenPuro = gerarTokenAleatorio();
        PasswordResetToken codigo = new PasswordResetToken();
        codigo.setUsuario(usuario);
        codigo.setTokenHash(sha256(tokenPuro));
        codigo.setExpiracao(LocalDateTime.now().plusMinutes(VALIDADE_RESET_MINUTOS));
        resetTokenRepository.save(codigo);
        return tokenPuro;
    }

    private PasswordResetToken consumirCodigo(String tokenPuro) {
        PasswordResetToken codigo = resetTokenRepository
                .findByTokenHash(sha256(tokenPuro.trim()))
                .filter(PasswordResetToken::isValido)
                .filter(c -> c.getUsuario().isAtivo())
                .orElseThrow(() -> new RegraDeNegocioException("Código inválido ou expirado"));
        codigo.setUsado(true);
        return codigo;
    }

    private static String normalizarEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
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
