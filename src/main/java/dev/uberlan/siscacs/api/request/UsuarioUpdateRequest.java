package dev.uberlan.siscacs.api.request;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioUpdateRequest(String login, @NotEmpty String nome, @NotEmpty String cacId) {
}
