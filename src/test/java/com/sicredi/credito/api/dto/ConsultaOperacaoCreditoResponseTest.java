package com.sicredi.credito.api.dto;

import com.sicredi.credito.domain.entity.OperacaoCredito;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaOperacaoCreditoResponseTest {

    @Test
    void deveMapearTodosOsCampos() {
        OperacaoCredito operacao = mock(OperacaoCredito.class);

        when(operacao.getIdOperacaoCredito()).thenReturn(1L);
        when(operacao.getIdAssociado()).thenReturn(2L);
        when(operacao.getValorOperacao()).thenReturn(new BigDecimal("123.45"));
        when(operacao.getSegmento()).thenReturn("AGRO");
        when(operacao.getCodigoProdutoCredito()).thenReturn("P01");
        when(operacao.getCodigoConta()).thenReturn("C123");
        when(operacao.getAreaBeneficiadaHa()).thenReturn(new BigDecimal("10"));
        LocalDateTime dt = LocalDateTime.now();
        when(operacao.getDataHoraContratacao()).thenReturn(dt);

        ConsultaOperacaoCreditoResponse dto = ConsultaOperacaoCreditoResponse.from(operacao);

        assertEquals(1L, dto.idOperacaoCredito());
        assertEquals(2L, dto.idAssociado());
        assertEquals(new BigDecimal("123.45"), dto.valorOperacao());
        assertEquals("AGRO", dto.segmento());
        assertEquals("P01", dto.codigoProdutoCredito());
        assertEquals("C123", dto.codigoConta());
        assertEquals(new BigDecimal("10"), dto.areaBeneficiadaHa());
        assertEquals(dt, dto.dataHoraContratacao());
    }
}