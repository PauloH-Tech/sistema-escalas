package br.com.paulo.escalas.entities.rodadas;

import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rodada_escala")
@Data
public class RodadaEscala {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDate data;

//    @Column(name = "nr_rodada", nullable = false)
//    private int numeroRodada;

    @OneToMany(mappedBy = "rodada")
//    @JsonIgnore
    private List<EscalaExtra> escalados;


}
