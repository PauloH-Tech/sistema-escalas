package br.com.paulo.escalas.entities.usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(@NotBlank String nome, @Email String email, @Size(min = 6) String senha) {
}
