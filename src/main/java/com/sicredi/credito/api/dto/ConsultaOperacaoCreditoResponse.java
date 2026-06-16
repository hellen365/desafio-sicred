package com.sicredi.credito.api.dto;

import com.sicredi.credito.api.entity.OperacaoCredito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaOperacaoCreditoResponse(
        Long idOperacaoCredito,
        Long idAssociado,
        BigDecimal valorOperacao,
        String segmento,
        String codigoProdutoCredito,
        String codigoConta,
        BigDecimal areaBeneficiadaHa,
        LocalDateTime dataHoraContratacao
) {

    public static ConsultaOperacaoCreditoResponse from(
            OperacaoCredito operacao) {

        return new ConsultaOperacaoCreditoResponse(
                operacao.getIdOperacaoCredito(),
                operacao.getIdAssociado(),
                operacao.getValorOperacao(),
                operacao.getSegmento(),
                operacao.getCodigoProdutoCredito(),
                operacao.getCodigoConta(),
                operacao.getAreaBeneficiadaHa(),
                operacao.getDataHoraContratacao()
        );
    }
}