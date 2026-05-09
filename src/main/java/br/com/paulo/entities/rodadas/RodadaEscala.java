package br.com.paulo.entities.rodadas;

import br.com.paulo.entities.escalas.EscalaExtra;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rodada_escala")
@Data
public class RodadaEscala {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Column(name = "nr_rodada")
    private int numeroRodada;

    @OneToMany(mappedBy = "rodada")
    @JsonIgnore
    private List<EscalaExtra> escalados;

}
