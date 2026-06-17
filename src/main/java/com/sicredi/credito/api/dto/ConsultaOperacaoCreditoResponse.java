package com.sicredi.credito.api.dto;


import com.sicredi.credito.domain.entity.OperacaoCredito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Dados da operação de crédito contratada")
public record ConsultaOperacaoCreditoResponse(

        @Schema(
                description = "Identificador único da operação de crédito",
                example = "1"
        )
        Long idOperacaoCredito,

        @Schema(
                description = "Identificador do associado",
                example = "12345"
        )
        Long idAssociado,

        @Schema(
                description = "Valor da operação de crédito",
                example = "8000.00"
        )
        BigDecimal valorOperacao,

        @Schema(
                description = "Segmento da operação",
                example = "AGRO"
        )
        String segmento,

        @Schema(
                description = "Código do produto de crédito",
                example = "903C"
        )
        String codigoProdutoCredito,

        @Schema(
                description = "Conta para depósito do valor contratado",
                example = "1234567890"
        )
        String codigoConta,

        @Schema(
                description = "Área beneficiada em hectares para operações AGRO",
                example = "150"
        )
        BigDecimal areaBeneficiadaHa,

        @Schema(
                description = "Data e hora da contratação",
                example = "2026-06-16T10:30:15"
        )
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