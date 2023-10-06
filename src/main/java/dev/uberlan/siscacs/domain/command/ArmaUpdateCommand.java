package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.Calibre;

import java.time.LocalDate;
import java.util.UUID;

public record ArmaUpdateCommand(UUID id, Calibre calibre, String descricao, LocalDate dataCompra, LocalDate dataVenda, String observacao) {
}
