package dev.uberlan.siscacs.api.request;

import java.time.LocalDate;
import java.util.UUID;

public record TreinamentoCreateRequest(LocalDate dataTreinamento, UUID armaId, Integer quantidadeTiros, Integer pontuacao, String observacao) {
}
