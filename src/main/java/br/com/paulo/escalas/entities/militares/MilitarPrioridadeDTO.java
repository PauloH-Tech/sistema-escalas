package br.com.paulo.escalas.entities.militares;

import java.time.LocalDate;
import java.util.UUID;

public record MilitarPrioridadeDTO(
        UUID idMilitar,
        String nome,
        Integer graduacao,
        LocalDate dtUltimaEscala,
        String tpAfastamento,
        Long qtEscalas){



    public Graduacao getGraduacao() {
        return Graduacao.fromCodigo(graduacao);
    }
//
//    public TipoAfastamento getTpAfastamento(){
//        return TipoAfastamento.valueOf(tpAfastamento);
//    }
}
