package br.com.paulo.entities.militares;

import br.com.paulo.entities.afastamentos.TipoAfastamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record MilitarPrioridadeDTO(
        UUID idMilitar,
        String nome,
        Patente patente,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data,
        TipoAfastamento tpAfastamento){

    public MilitarPrioridadeDTO(
            UUID idMilitar,
            String nome,
            Integer patenteInteger,
            LocalDate data,
            TipoAfastamento tpAfastamento) {
        this.idMilitar = idMilitar;
        this.nome = nome;
        this.patente = Patente.fromCodigo(patenteInteger);
        this.data = data;
        this.tpAfastamento = tpAfastamento;
    }
}
