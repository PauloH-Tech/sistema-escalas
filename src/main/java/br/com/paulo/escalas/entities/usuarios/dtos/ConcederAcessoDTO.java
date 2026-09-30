package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Concede acesso ao app para um militar: cria o usuário sem senha (o militar define nome e senha no primeiro acesso).
 *
 * @param role padrão USER
 */
public record ConcederAcessoDTO(
        @NotNull UUID militarId,
        @NotBlank @Email String email,
        UserRole role
) {}
