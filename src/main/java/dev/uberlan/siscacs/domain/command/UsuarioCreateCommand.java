package dev.uberlan.siscacs.domain.command;

public record UsuarioCreateCommand(String login, String nome, String password, String cacId) {
}
