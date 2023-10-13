package dev.uberlan.siscacs.api.request;

import dev.uberlan.siscacs.domain.Calibre;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ArmaCreateRequest(@NotNull Calibre calibre, @NotEmpty String descricao, @NotNull LocalDate dataCompra, String observacao) {
}
