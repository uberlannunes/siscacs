package dev.uberlan.siscacs.domain.command;

import dev.uberlan.siscacs.domain.Calibre;

import java.time.LocalDate;
import java.util.UUID;

public record ArmaCreateCommand(Calibre calibre, String descricao, LocalDate dataCompra, UUID usuarioId, String observacao) {
}
