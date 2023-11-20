package dev.uberlan.siscacs.domain.command;

import java.util.UUID;

public record UsuarioUpdatePasswordCommand(UUID id, String password) {
}
