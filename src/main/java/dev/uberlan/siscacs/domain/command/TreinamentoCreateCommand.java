package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.dto.ArmaDTO;

import java.time.LocalDate;

public record TreinamentoCreateCommand(LocalDate dataTreinamento, ArmaDTO arma, int quantidadeTiros, int pontuacao, String observacao) {
}
