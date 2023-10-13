package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class UsuarioNotFoundException extends RuntimeException {
    
    public UsuarioNotFoundException(String id) {
        super(String.format("Usuário com o id=%s não foi localizado.", id));
    }
    
    public static UsuarioNotFoundException of(String id) {
        return new UsuarioNotFoundException(id);
    }
}
