package com.sicredi.credito.api.dto;

import java.math.BigDecimal;

public record ContratacaoCreditoRequest(
        Long idAssociado,
        BigDecimal valorOperacao,
        String segmento,
        String codigoProdutoCredito,
        String codigoConta,
        BigDecimal areaBeneficiadaHa
) {}