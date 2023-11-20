package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class TreinamentoNotFoundException extends RuntimeException {

    public TreinamentoNotFoundException(UUID id) {
        super(String.format("Treinamento com o id=%s não foi localizado.", id.toString()));
    }
    
    public static TreinamentoNotFoundException of(UUID id) {
        return new TreinamentoNotFoundException(id);
    }
}
