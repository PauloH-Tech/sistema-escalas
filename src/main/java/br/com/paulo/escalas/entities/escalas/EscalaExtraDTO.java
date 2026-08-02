package br.com.paulo.escalas.entities.escalas;

import java.util.List;
import java.util.UUID;

public record EscalaExtraDTO(List<UUID> militarIds, UUID rodadaId) {
}
