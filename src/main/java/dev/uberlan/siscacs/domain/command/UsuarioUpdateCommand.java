package dev.uberlan.siscacs.domain.command;

import java.util.UUID;

public record UsuarioUpdateCommand(UUID id, String nome, String password, String cacId) {
}
