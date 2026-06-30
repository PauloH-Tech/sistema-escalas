package br.com.paulo.entities.afastamentos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record AfastamentoDTO(TipoAfastamento tpAfastamento,
                             LocalDate dtInicio,
                             LocalDate dtFim) {
}
