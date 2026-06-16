package com.sicredi.credito.api.service.exception;

public class OperacaoCreditoNaoEncontradaException
        extends RuntimeException {

    public OperacaoCreditoNaoEncontradaException(Long id) {
        super("Operação de crédito não encontrada: " + id);
    }
}
