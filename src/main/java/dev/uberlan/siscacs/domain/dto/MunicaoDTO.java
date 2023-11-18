package dev.uberlan.siscacs.domain.dto;

import java.util.UUID;

public record MunicaoDTO(UUID id, ArmaDTO arma, int quantidade) {
}
