package com.sicredi.credito.application.strategy;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.domain.exception.NegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgroCreditoStrategyTest {

    private final AgroCreditoStrategy strategy = new AgroCreditoStrategy();

    @Test
    void getSegmento_deveRetornarAGRO() {
        assertEquals("AGRO", strategy.getSegmento());
    }

    @Test
    void validar_deveLancarQuandoAreaNull() {
        ContratacaoCreditoRequest req = mock(ContratacaoCreditoRequest.class);
        when(req.areaBeneficiadaHa()).thenReturn(null);

        assertThrows(NegocioException.class, () -> strategy.validar(req));
    }

    @Test
    void validar_deveLancarQuandoAreaZeroOuNegativa() {
        ContratacaoCreditoRequest req = mock(ContratacaoCreditoRequest.class);
        when(req.areaBeneficiadaHa()).thenReturn(BigDecimal.ZERO);

        assertThrows(NegocioException.class, () -> strategy.validar(req));

        when(req.areaBeneficiadaHa()).thenReturn(BigDecimal.valueOf(-1));
        assertThrows(NegocioException.class, () -> strategy.validar(req));
    }

    @Test
    void validar_naoDeveLancarQuandoAreaPositiva() {
        ContratacaoCreditoRequest req = mock(ContratacaoCreditoRequest.class);
        when(req.areaBeneficiadaHa()).thenReturn(BigDecimal.valueOf(1));

        assertDoesNotThrow(() -> strategy.validar(req));
    }
}