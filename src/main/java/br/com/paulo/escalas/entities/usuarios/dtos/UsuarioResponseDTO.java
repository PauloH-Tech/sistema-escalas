package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String nome, String email, UserRole role, boolean ativo) {

    public static UsuarioResponseDTO de(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getRole(), u.isAtivo());
    }
}
