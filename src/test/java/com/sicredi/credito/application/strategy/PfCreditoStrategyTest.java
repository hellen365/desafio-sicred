package com.sicredi.credito.application.strategy;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.domain.entity.OperacaoCredito;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PfCreditoStrategyTest {

    private final PfCreditoStrategy strategy = new PfCreditoStrategy();

    @Test
    void getSegmento_retornPF() {
        assertEquals("PF", strategy.getSegmento());
    }

    @Test
    void validar_naoLancaExcecao() {
        ContratacaoCreditoRequest request = Mockito.mock(ContratacaoCreditoRequest.class);
        assertDoesNotThrow(() -> strategy.validar(request));
    }

    @Test
    void processarPosContratacao_naoLancaExcecao_eSemInteracoes() {
        OperacaoCredito operacao = Mockito.mock(OperacaoCredito.class);
        ContratacaoCreditoRequest request = Mockito.mock(ContratacaoCreditoRequest.class);

        assertDoesNotThrow(() -> strategy.processarPosContratacao(operacao, request));

        Mockito.verifyNoInteractions(operacao, request);
    }
}