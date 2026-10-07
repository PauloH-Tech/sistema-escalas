package br.com.paulo.escalas.entities.usuarios.dtos;

import br.com.paulo.escalas.entities.usuarios.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarAcessoDTO(
        @NotBlank @Email String email,
        @NotNull UserRole role
) {}
