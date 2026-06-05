package br.com.paulo.entities.militares;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record MilitarPrioridadeDTO(
        UUID idMilitar,
        String nome,
        Integer patente,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data,
        String tpAfastamento,
        Long qtEscalas){



    public Graduacao getPatente() {
        return Graduacao.fromCodigo(patente);
    }
//
//    public TipoAfastamento getTpAfastamento(){
//        return TipoAfastamento.valueOf(tpAfastamento);
//    }
}
