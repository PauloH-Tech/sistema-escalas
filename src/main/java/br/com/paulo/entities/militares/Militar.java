package br.com.paulo.entities.militares;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "militares")
@Data
public class Militar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private boolean st_ativo;

}
