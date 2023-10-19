package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class ArmaInvalidDataException extends RuntimeException {

    public ArmaInvalidDataException(UUID id) {
        super(String.format("Arma com o id=%s possui dados inválidos.", id.toString()));
    }
    
    public static ArmaInvalidDataException of(UUID id) {
        return new ArmaInvalidDataException(id);
    }
}
