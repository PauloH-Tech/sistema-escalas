package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;

import java.util.UUID;

public record TokenResponseDTO(
        String token,
        String tipo,
        String nome,
        UserRole role,
        UUID militarId
) {
    public static TokenResponseDTO de(String token, Usuario usuario) {
        UUID militarId = usuario.getMilitar() != null ? usuario.getMilitar().getId() : null;
        return new TokenResponseDTO(token, "Bearer", usuario.getNome(), usuario.getRole(), militarId);
    }
}
