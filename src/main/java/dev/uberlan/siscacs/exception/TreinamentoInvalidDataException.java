package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class TreinamentoInvalidDataException extends RuntimeException {

    public TreinamentoInvalidDataException(UUID id) {
        super(String.format("Treinamento com o id=%s possui dados inválidos.", id.toString()));
    }
    
    public static TreinamentoInvalidDataException of(UUID id) {
        return new TreinamentoInvalidDataException(id);
    }
}
