package com.sicredi.credito.api.service;

import com.sicredi.credito.api.client.ProdutoCreditoClient;
import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.api.entity.OperacaoCredito;
import com.sicredi.credito.api.entity.OperacaoCreditoPj;
import com.sicredi.credito.api.repository.OperacaoCreditoPjRepository;
import com.sicredi.credito.api.repository.OperacaoCreditoRepository;
import com.sicredi.credito.api.service.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperacaoCreditoServiceTest {

    @Mock
    private OperacaoCreditoRepository repository;

    @Mock
    private OperacaoCreditoPjRepository pjRepository;

    @Mock
    private ProdutoCreditoClient produtoCreditoClient;

    @InjectMocks
    private OperacaoCreditoService service;

    @Test
    void deveRecusarOperacaoAgroSemAreaBeneficiada() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(5000),
                        "AGRO",
                        "903C",
                        "1234567890",
                        null
                );

        assertThrows(
                NegocioException.class,
                () -> service.contratar(request)
        );
    }

    @Test
    void deveRecusarQuandoProdutoNaoPermitirContratacao() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(5000),
                        "PF",
                        "101A",
                        "1234567890",
                        null
                );

        when(produtoCreditoClient.permiteContratacao(
                anyString(),
                anyString(),
                any()))
                .thenReturn(false);

        assertThrows(
                NegocioException.class,
                () -> service.contratar(request)
        );
    }

    @Test
    void deveContratarOperacaoPjComSucesso() {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(50000),
                        "PJ",
                        "505E",
                        "1234567890",
                        null
                );

        when(produtoCreditoClient.permiteContratacao(
                anyString(),
                anyString(),
                any()))
                .thenReturn(true);

        OperacaoCredito operacaoSalva =
                OperacaoCredito.from(request);

        operacaoSalva.setIdOperacaoCredito(1L);

        when(repository.save(any(OperacaoCredito.class)))
                .thenReturn(operacaoSalva);

        Long id = service.contratar(request);

        assertEquals(1L, id);

        verify(repository).save(any(OperacaoCredito.class));
        verify(pjRepository).save(any(OperacaoCreditoPj.class));
    }

    @Test
    void deveConsultarOperacaoPorId() {

        OperacaoCredito operacao = new OperacaoCredito();
        operacao.setIdOperacaoCredito(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(operacao));

        ConsultaOperacaoCreditoResponse response =
                service.consultar(1L);

        assertEquals(1L, response.idOperacaoCredito());
    }
}
