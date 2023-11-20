package dev.uberlan.siscacs.domain.command;

import java.util.UUID;

public record UsuarioUpdateDadosPessoaisCommand(UUID id, String nome, String cacId) {
}
