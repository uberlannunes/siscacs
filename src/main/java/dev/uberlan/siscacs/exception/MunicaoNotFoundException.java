package dev.uberlan.siscacs.exception;

import java.util.UUID;

public class MunicaoNotFoundException extends RuntimeException {

    public MunicaoNotFoundException(UUID id) {
        super(String.format("Munição com o id=%s não foi localizada.", id.toString()));
    }
    
    public static MunicaoNotFoundException of(UUID id) {
        return new MunicaoNotFoundException(id);
    }
}
