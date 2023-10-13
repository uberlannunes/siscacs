package dev.uberlan.siscacs.api.request;

public record UsuarioCreateRequest(String login, String nome, String password, String passwordRepeat, String cacId) {
}
