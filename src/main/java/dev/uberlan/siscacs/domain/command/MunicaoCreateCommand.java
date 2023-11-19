package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.dto.ArmaDTO;

public record MunicaoCreateCommand(ArmaDTO arma, int quantidade) {
}
