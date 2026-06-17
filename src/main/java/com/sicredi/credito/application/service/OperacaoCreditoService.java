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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OperacaoCreditoService {

    private final OperacaoCreditoRepository repository;
    private final SegmentoCreditoFactory factory;
    private final ProdutoCreditoClient produtoCreditoClient;

    public OperacaoCreditoService(OperacaoCreditoRepository repository,
                                  SegmentoCreditoFactory factory,
                                  ProdutoCreditoClient produtoCreditoClient
    ) {
        this.repository = repository;
        this.factory = factory;
        this.produtoCreditoClient = produtoCreditoClient;
    }


    public ConsultaOperacaoCreditoResponse consultar(Long idOperacaoCredito) {

        OperacaoCredito operacao = repository.findById(idOperacaoCredito)
                .orElseThrow(() ->
                        new OperacaoCreditoNaoEncontradaException(idOperacaoCredito));

        return ConsultaOperacaoCreditoResponse.from(operacao);
    }

    @Transactional
    public Long contratar(
            ContratacaoCreditoRequest request) {

        SegmentoCreditoStrategy strategy =
                factory.getStrategy(request.segmento());

        strategy.validar(request);

        boolean permite =
                produtoCreditoClient.permiteContratacao(
                        request.codigoProdutoCredito(),
                        request.segmento(),
                        request.valorOperacao());

        if (!permite) {
            throw new NegocioException(
                    "Produto não permite contratação");
        }

        OperacaoCredito operacao =
                repository.save(
                        OperacaoCredito.from(request));

        strategy.processarPosContratacao(
                operacao,
                request);

        return operacao.getIdOperacaoCredito();
    }
}
