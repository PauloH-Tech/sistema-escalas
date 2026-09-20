package br.com.paulo.escalas.controllers;

import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.*;
import br.com.paulo.escalas.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

   private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO dto) {
        return authService.autenticar(dto);
    }

    /** Front chama isso na inicialização pra saber quem está logado. */
    @GetMapping("/me")
    public UsuarioResponseDTO me(@AuthenticationPrincipal Usuario usuario) {
        return UsuarioResponseDTO.de(usuario);
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void esqueciSenha(@RequestBody @Valid ForgotPasswordDTO dto) {
        authService.solicitarResetSenha(dto.email());
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetarSenha(@RequestBody @Valid ResetPasswordDTO dto) {
        authService.resetarSenha(dto);
    }

}
