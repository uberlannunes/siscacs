package dev.uberlan.siscacs.api.request;

import java.time.LocalDate;
import java.util.UUID;

public record TreinamentoUpdateRequest(UUID id, LocalDate dataTreinamento, UUID armaId, Integer quantidadeTiros, Integer pontuacao, String observacao) {
}
