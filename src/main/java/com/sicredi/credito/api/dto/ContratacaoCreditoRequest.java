package com.sicredi.credito.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContratacaoCreditoRequest(

        @Schema(example = "12345")
        Long idAssociado,

        @Schema(example = "50000")
        BigDecimal valorOperacao,

        @Schema(example = "PJ")
        String segmento,

        @Schema(example = "505E")
        String codigoProdutoCredito,

        @Schema(example = "1234567890")
        String codigoConta,

        @Schema(example = "150")
        BigDecimal areaBeneficiadaHa
) {}