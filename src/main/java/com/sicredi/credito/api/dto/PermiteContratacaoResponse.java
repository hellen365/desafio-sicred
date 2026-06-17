package com.sicredi.credito.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da contratação de uma operação de crédito")
public record PermiteContratacaoResponse(

        @Schema(
                description = "Identificador da operação de crédito gerada",
                example = "1"
        )
        Boolean permiteContratar
) {}
