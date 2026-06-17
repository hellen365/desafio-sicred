
package com.sicredi.credito.api.controller;

import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.application.service.OperacaoCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operacoes-credito")
@RequiredArgsConstructor
@Tag(name = "Operações de Crédito")
public class ConsultarOperacaoCreditoController {

    private final OperacaoCreditoService service;


    @Operation(summary = "Consultar operação de crédito por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Operação encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ConsultaOperacaoCreditoResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Operação não encontrada",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                            {
                              "mensagem": "Operação de crédito 1 não encontrada"
                            }
                            """
                            )
                    )
            )
    })
    @GetMapping("/{idOperacaoCredito}")
    public ResponseEntity<ConsultaOperacaoCreditoResponse> consultar(
            @PathVariable Long idOperacaoCredito) {

        return ResponseEntity.ok(
                service.consultar(idOperacaoCredito));
    }
}
