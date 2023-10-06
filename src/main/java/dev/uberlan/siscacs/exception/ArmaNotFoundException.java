package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class ArmaNotFoundException extends RuntimeException {

    public ArmaNotFoundException(UUID id) {
        super(String.format("Arma com o id=%s não foi localizada.", id.toString()));
    }
    
    public static ArmaNotFoundException of(UUID id) {
        return new ArmaNotFoundException(id);
    }
}
