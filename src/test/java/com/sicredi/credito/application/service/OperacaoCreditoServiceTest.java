package com.sicredi.credito.application.service;

import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.application.factory.SegmentoCreditoFactory;
import com.sicredi.credito.application.strategy.SegmentoCreditoStrategy;
import com.sicredi.credito.domain.entity.OperacaoCredito;
import com.sicredi.credito.domain.exception.NegocioException;
import com.sicredi.credito.domain.exception.OperacaoCreditoNaoEncontradaException;
import com.sicredi.credito.infrastructure.client.ProdutoCreditoClient;
import com.sicredi.credito.infrastructure.repository.OperacaoCreditoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperacaoCreditoServiceTest {

    @Mock
    private OperacaoCreditoRepository repository;

    @Mock
    private SegmentoCreditoFactory factory;

    @Mock
    private SegmentoCreditoStrategy strategy;

    @Mock
    private ProdutoCreditoClient produtoCreditoClient;

    @InjectMocks
    private OperacaoCreditoService service;

    @Test
    void deveContratarOperacaoComSucesso() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(50000),
                        "PJ",
                        "505E",
                        "1234567890",
                        null
                );

        OperacaoCredito operacao = OperacaoCredito.from(request);
        operacao.setIdOperacaoCredito(1L);

        when(factory.getStrategy("PJ"))
                .thenReturn(strategy);

        when(produtoCreditoClient.permiteContratacao(
                anyString(),
                anyString(),
                any()))
                .thenReturn(true);

        when(repository.save(any(OperacaoCredito.class)))
                .thenReturn(operacao);

        Long id = service.contratar(request);

        assertEquals(1L, id);

        verify(strategy).validar(request);
        verify(strategy).processarPosContratacao(
                operacao,
                request);

        verify(repository).save(any(OperacaoCredito.class));
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoPermitirContratacao() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(3000),
                        "PF",
                        "101A",
                        "1234567890",
                        null
                );

        when(factory.getStrategy("PF"))
                .thenReturn(strategy);

        when(produtoCreditoClient.permiteContratacao(
                anyString(),
                anyString(),
                any()))
                .thenReturn(false);

        NegocioException exception =
                assertThrows(
                        NegocioException.class,
                        () -> service.contratar(request)
                );

        assertEquals(
                "Produto não permite contratação",
                exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void deveConsultarOperacaoPorId() {

        OperacaoCredito operacao = new OperacaoCredito();
        operacao.setIdOperacaoCredito(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(operacao));

        ConsultaOperacaoCreditoResponse response =
                service.consultar(1L);

        assertNotNull(response);
        assertEquals(1L, response.idOperacaoCredito());
    }

    @Test
    void deveLancarExcecaoQuandoOperacaoNaoExistir() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                OperacaoCreditoNaoEncontradaException.class,
                () -> service.consultar(1L)
        );
    }

    @Test
    void devePropagarExcecaoDaValidacaoDaStrategy() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(8000),
                        "AGRO",
                        "903C",
                        "1234567890",
                        null
                );

        when(factory.getStrategy("AGRO"))
                .thenReturn(strategy);

        doThrow(new NegocioException(
                "Área beneficiada é obrigatória"))
                .when(strategy)
                .validar(request);

        assertThrows(
                NegocioException.class,
                () -> service.contratar(request)
        );

        verify(repository, never()).save(any());
    }
}