package com.sicredi.credito.api.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> handleNegocioException(
            NegocioException ex) {

        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(OperacaoCreditoNaoEncontradaException.class)
    public ResponseEntity<String> handleNotFound(
            OperacaoCreditoNaoEncontradaException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
