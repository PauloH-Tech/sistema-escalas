package br.com.paulo.escalas.entities.afastamentos;

import br.com.paulo.escalas.entities.militares.Militar;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "afastamento")
@Data
public class Afastamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAfastamento tpAfastamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Militar militar;

    @Column(nullable = false)
    private LocalDate dtInicio;

    @Column(nullable = false)
    private LocalDate dtFim;



}
