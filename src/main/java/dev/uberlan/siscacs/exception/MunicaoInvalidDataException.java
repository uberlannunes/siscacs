package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class MunicaoInvalidDataException extends RuntimeException {

    public MunicaoInvalidDataException(UUID id) {
        super(String.format("Muniçao com o id=%s possui dados inválidos.", id.toString()));
    }
    
    public static MunicaoInvalidDataException of(UUID id) {
        return new MunicaoInvalidDataException(id);
    }
}
