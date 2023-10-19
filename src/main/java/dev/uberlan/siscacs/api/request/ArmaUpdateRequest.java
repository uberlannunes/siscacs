package dev.uberlan.siscacs.api.request;

import dev.uberlan.siscacs.domain.Calibre;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ArmaUpdateRequest(@NotNull UUID id, @NotNull Calibre calibre, @NotEmpty String descricao, @NotNull LocalDate dataCompra, LocalDate dataVenda, String observacao) {
}
