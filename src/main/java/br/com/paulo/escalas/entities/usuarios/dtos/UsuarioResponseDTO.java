package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id,
                                 String nome,
                                 String email,
                                 UserRole role,
                                 boolean ativo,
                                 boolean primeiroAcessoPendente,
                                 UUID militarId) {

    public static UsuarioResponseDTO de(Usuario u) {
        UUID militarId = u.getMilitar() != null ? u.getMilitar().getId() : null;
        return new UsuarioResponseDTO(u.getId(), u.getNomeExibicao(), u.getEmail(), u.getRole(), u.isAtivo(),
                u.isPrimeiroAcessoPendente(), militarId);
    }
}
