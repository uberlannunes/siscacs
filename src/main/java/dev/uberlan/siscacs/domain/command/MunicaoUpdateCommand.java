package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.dto.ArmaDTO;

import java.util.UUID;

public record MunicaoUpdateCommand(UUID id, ArmaDTO arma, int quantidade) {
}
