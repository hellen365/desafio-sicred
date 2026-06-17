
package com.sicredi.credito.api.controller;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.api.dto.ContratacaoCreditoResponse;
import com.sicredi.credito.application.service.OperacaoCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operacoes-credito")
@RequiredArgsConstructor
@Tag(name = "Operações de Crédito")
public class ContratarOperacaoCreditoController {

    private final OperacaoCreditoService service;

    @Operation(summary = "Contratar operação de crédito")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Operação contratada com sucesso",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ContratacaoCreditoResponse.class
                            ),
                            examples = @ExampleObject(
                                    value = """
                            {
                              "idOperacaoCredito": 1
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Regra de negócio violada",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                            {
                              "mensagem": "Produto não permite contratação"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Serviço de produtos indisponível",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                            {
                              "mensagem": "Serviço de produtos indisponível"
                            }
                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ContratacaoCreditoResponse> contratar(
            @RequestBody ContratacaoCreditoRequest request) {

        Long id = service.contratar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ContratacaoCreditoResponse(id));
    }
}
