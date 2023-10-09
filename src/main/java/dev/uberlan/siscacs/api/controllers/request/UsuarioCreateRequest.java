package dev.uberlan.siscacs.api.controllers.request;

public record UsuarioCreateRequest(String login, String nome, String password, String passwordRepeat, String cacId) {
}
