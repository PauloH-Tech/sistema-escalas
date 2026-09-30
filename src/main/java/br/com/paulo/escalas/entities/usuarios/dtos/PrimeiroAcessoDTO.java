package br.com.paulo.escalas.entities.usuarios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PrimeiroAcessoDTO(
        @NotBlank String token,
        @NotBlank @Size(max = 255) String nome,
        @NotBlank @Size(min = 6, message = "A senha deve ter ao menos 6 caracteres") String senha
) {}
