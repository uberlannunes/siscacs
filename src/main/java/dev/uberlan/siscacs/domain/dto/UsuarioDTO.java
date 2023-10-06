package dev.uberlan.siscacs.domain.dto;

import java.util.UUID;

public record UsuarioDTO(UUID id, String login, String nome, String cacId) {
}
