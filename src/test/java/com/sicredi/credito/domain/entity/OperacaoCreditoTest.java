package com.sicredi.credito.domain.entity;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OperacaoCreditoTest {

    @Test
    void from_deveMapearTodosOsAtributos() {
        ContratacaoCreditoRequest request = new ContratacaoCreditoRequest(
                12345L,
                BigDecimal.valueOf(50000),
                "PJ",
                "505E",
                "1234567890",
                BigDecimal.valueOf(12.5)
        );

        OperacaoCredito operacao = OperacaoCredito.from(request);

        assertNotNull(operacao);
        assertEquals(request.idAssociado(), operacao.getIdAssociado());
        assertEquals(request.valorOperacao(), operacao.getValorOperacao());
        assertEquals(request.segmento(), operacao.getSegmento());
        assertEquals(request.codigoProdutoCredito(), operacao.getCodigoProdutoCredito());
        assertEquals(request.codigoConta(), operacao.getCodigoConta());
        assertEquals(request.areaBeneficiadaHa(), operacao.getAreaBeneficiadaHa());
        assertNotNull(operacao.getDataHoraContratacao());
        assertFalse(operacao.getDataHoraContratacao().isAfter(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void from_deveAceitarAreaBeneficiadaNula() {
        ContratacaoCreditoRequest request = new ContratacaoCreditoRequest(
                54321L,
                BigDecimal.valueOf(3000),
                "PF",
                "101A",
                "0987654321",
                null
        );

        OperacaoCredito operacao = OperacaoCredito.from(request);

        assertNotNull(operacao);
        assertNull(operacao.getAreaBeneficiadaHa());
        assertEquals(request.idAssociado(), operacao.getIdAssociado());
        assertNotNull(operacao.getDataHoraContratacao());
    }
}