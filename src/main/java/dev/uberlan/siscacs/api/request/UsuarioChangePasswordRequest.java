package dev.uberlan.siscacs.api.request;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioChangePasswordRequest(@NotEmpty String password, @NotEmpty String passwordNew, @NotEmpty String passwordNewConfirm) {
}
