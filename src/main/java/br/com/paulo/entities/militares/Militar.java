package br.com.paulo.entities.militares;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "militares")
@Data
public class Militar {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nm_militar", nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private Boolean st_ativo;

    private Graduacao graduacao;

}
