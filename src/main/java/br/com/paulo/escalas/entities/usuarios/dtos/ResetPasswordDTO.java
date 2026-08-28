package br.com.paulo.escalas.entities.usuarios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(@NotBlank String token, @Size(min = 6) String novaSenha) {
}
