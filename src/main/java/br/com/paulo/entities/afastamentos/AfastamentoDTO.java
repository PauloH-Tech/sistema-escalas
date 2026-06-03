package br.com.paulo.entities.afastamentos;

import java.time.LocalDate;

public record AfastamentoDTO(TipoAfastamento tpAfastamento, LocalDate dtInicio, LocalDate dtFim) {
}
