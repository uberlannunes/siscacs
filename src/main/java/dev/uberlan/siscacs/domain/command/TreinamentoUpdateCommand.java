package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.dto.ArmaDTO;

import java.time.LocalDate;
import java.util.UUID;

public record TreinamentoUpdateCommand(UUID id, LocalDate dataTreinamento, ArmaDTO arma, int quantidadeTiros, int pontuacao, String observacao) {
}
