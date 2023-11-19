package dev.uberlan.siscacs.api.request;

import java.util.UUID;

public record MunicaoUpdateRequest(UUID id, UUID armaId, int quantidade) {
}
