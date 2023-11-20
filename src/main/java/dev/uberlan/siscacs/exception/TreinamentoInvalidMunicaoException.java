package dev.uberlan.siscacs.exception;

public class TreinamentoInvalidMunicaoException extends RuntimeException {

    public TreinamentoInvalidMunicaoException(String message) {
        super(message);
    }
    
    public static TreinamentoInvalidMunicaoException of(String message) {
        return new TreinamentoInvalidMunicaoException(message);
    }
}
