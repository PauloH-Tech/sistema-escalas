package br.com.paulo.entities.rodadas;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record RodadaDTO(
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate data) {
}
