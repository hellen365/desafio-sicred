package com.sicredi.credito.domain.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNegocioException_shouldReturnBadRequestWithMessage() {
        String msg = "negocio falhou";
        NegocioException ex = new NegocioException(msg);
        ResponseEntity<String> response = handler.handleNegocioException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(msg, response.getBody());
    }

    @Test
    void handleNotFound_shouldReturnNotFoundWithMessage() {
        String msg = "Operação de crédito não encontrada: 1234";
        OperacaoCreditoNaoEncontradaException ex = new OperacaoCreditoNaoEncontradaException(1234L);
        ResponseEntity<String> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(msg, response.getBody());
    }

    @Test
    void handleServiceUnavailable_shouldReturnServiceUnavailableWithMessage() {
        String msg = "servico indisponivel";
        ServiceUnavailableException ex = new ServiceUnavailableException(msg);
        ResponseEntity<String> response = handler.handleServiceUnavailable(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals(msg, response.getBody());
    }
}