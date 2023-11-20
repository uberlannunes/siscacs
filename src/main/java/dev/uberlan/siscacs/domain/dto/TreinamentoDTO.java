package dev.uberlan.siscacs.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TreinamentoDTO(UUID id, LocalDate dataTreinamento, ArmaDTO arma, int quantidadeTiros, int pontuacao, String observacao) {
}
