package br.com.paulo.entities.escalas;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.rodadas.RodadaEscala;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "escala_extra")
@Data
public class EscalaExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Militar militar;

    @ManyToOne
    @JoinColumn(nullable = false)
    private RodadaEscala rodada;
}
