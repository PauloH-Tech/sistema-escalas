package br.com.paulo.entities.escalas;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.rodadas.RodadaEscala;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "escala_extra")
@Data
public class EscalaExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Militar militar;

    @ManyToOne
    private RodadaEscala rodada;
}
