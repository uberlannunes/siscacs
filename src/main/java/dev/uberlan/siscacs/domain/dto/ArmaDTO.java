package dev.uberlan.siscacs.domain.dto;

import dev.uberlan.siscacs.domain.Calibre;

import java.time.LocalDate;
import java.util.UUID;

public record ArmaDTO(UUID id, Calibre calibre, String descricao, LocalDate dataCompra, LocalDate dataVenda, UUID usuarioId, String observacao) {
    public ArmaDTO(UUID id) {
        this(id, null, null, null, null, null, null);
    }

    public ArmaDTO(UUID id, Calibre calibre, String descricao) {
        this(id, calibre, descricao, null, null, null, null);
    }

    public ArmaDTO(UUID id, Calibre calibre) {
        this(id, calibre, null, null, null, null, null);
    }
}
