package br.com.paulo.escalas.entities.usuarios;

import jakarta.persistence.*;

import java.util.UUID;

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean ativo;


}
