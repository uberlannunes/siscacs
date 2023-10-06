package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class UsuarioNotFoundException extends RuntimeException {
    
    public UsuarioNotFoundException(UUID id) {
        super(String.format("Usuário com o id=%s não foi localizado.", id.toString()));
    }
    
    public static UsuarioNotFoundException of(UUID id) {
        return new UsuarioNotFoundException(id);
    }
}
