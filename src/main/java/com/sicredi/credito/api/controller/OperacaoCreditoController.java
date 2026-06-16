package com.sicredi.credito.api.controller;

import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.api.dto.ContratacaoCreditoResponse;
import com.sicredi.credito.api.service.OperacaoCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operacoes-credito")
@RequiredArgsConstructor
public class OperacaoCreditoController {

    private final OperacaoCreditoService service;

    @PostMapping
    public ResponseEntity<ContratacaoCreditoResponse> contratar(
            @RequestBody ContratacaoCreditoRequest request) {

        Long id = service.contratar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ContratacaoCreditoResponse(id));
    }

    @GetMapping("/{idOperacaoCredito}")
    public ResponseEntity<ConsultaOperacaoCreditoResponse> consultar(
            @PathVariable Long idOperacaoCredito) {

        return ResponseEntity.ok(
                service.consultar(idOperacaoCredito));
    }
}
