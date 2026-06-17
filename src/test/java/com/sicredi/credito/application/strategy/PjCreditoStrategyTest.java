package com.sicredi.credito.application.strategy;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.domain.entity.OperacaoCredito;
import com.sicredi.credito.domain.entity.OperacaoCreditoPj;
import com.sicredi.credito.infrastructure.repository.OperacaoCreditoPjRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PjCreditoStrategyTest {

    private final OperacaoCreditoPjRepository repository = mock(OperacaoCreditoPjRepository.class);
    private final PjCreditoStrategy strategy = new PjCreditoStrategy(repository);

    @Test
    void getSegmento_deveRetornarPJ() {
        assertEquals("PJ", strategy.getSegmento());
    }

    @Test
    void processarPosContratacao_deveSalvarOperacaoCreditoPj() {
        OperacaoCredito operacao = mock(OperacaoCredito.class);
        when(operacao.getIdOperacaoCredito()).thenReturn(42L);

        ContratacaoCreditoRequest request = mock(ContratacaoCreditoRequest.class);
        when(request.idAssociado()).thenReturn(123L);

        strategy.processarPosContratacao(operacao, request);

        verify(repository, times(1)).save(any(OperacaoCreditoPj.class));
    }

    @Test
    void validar_naoDeveLancarExcecao() {
        ContratacaoCreditoRequest request = mock(ContratacaoCreditoRequest.class);
        assertDoesNotThrow(() -> strategy.validar(request));
    }
}