package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;

public record TokenResponseDTO(
        String token,
        String tipo,
        String nome,
        UserRole role
) {
    public static TokenResponseDTO de(String token, Usuario usuario) {
        return new TokenResponseDTO(token, "Bearer", usuario.getNome(), usuario.getRole());
    }
}
