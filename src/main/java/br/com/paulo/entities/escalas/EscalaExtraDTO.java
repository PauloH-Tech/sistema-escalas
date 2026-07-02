package br.com.paulo.entities.escalas;

import java.util.List;
import java.util.UUID;

public record EscalaExtraDTO(List<UUID> militarIds, UUID rodadaId) {
}
