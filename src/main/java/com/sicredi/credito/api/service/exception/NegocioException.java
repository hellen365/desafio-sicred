package com.sicredi.credito.api.service.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
