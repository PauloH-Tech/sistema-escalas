package br.com.paulo.escalas.entities.usuarios.dtos;

import jakarta.validation.constraints.Email;

public record ForgotPasswordDTO(@Email String email) {
}
