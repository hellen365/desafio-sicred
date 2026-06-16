package com.sicredi.credito.api.service;

import com.sicredi.credito.api.client.ProdutoCreditoClient;
import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.api.entity.OperacaoCredito;
import com.sicredi.credito.api.entity.OperacaoCreditoPj;
import com.sicredi.credito.api.repository.OperacaoCreditoPjRepository;
import com.sicredi.credito.api.repository.OperacaoCreditoRepository;
import com.sicredi.credito.api.service.exception.NegocioException;
import com.sicredi.credito.api.service.exception.OperacaoCreditoNaoEncontradaException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class OperacaoCreditoService {

    private final OperacaoCreditoRepository repository;
    private final OperacaoCreditoPjRepository pjRepository;
    private final ProdutoCreditoClient produtoCreditoClient;

    public OperacaoCreditoService(OperacaoCreditoRepository repository,
                                  OperacaoCreditoPjRepository pjRepository,
                                  ProdutoCreditoClient produtoCreditoClient
    ) {
        this.repository = repository;
        this.pjRepository = pjRepository;
        this.produtoCreditoClient = produtoCreditoClient;
    }


    public ConsultaOperacaoCreditoResponse consultar(Long idOperacaoCredito) {

        OperacaoCredito operacao = repository.findById(idOperacaoCredito)
                .orElseThrow(() ->
                        new OperacaoCreditoNaoEncontradaException(idOperacaoCredito));

        return ConsultaOperacaoCreditoResponse.from(operacao);
    }

    public Long contratar(ContratacaoCreditoRequest request) {

        validarAgro(request);

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
                repository.save(OperacaoCredito.from(request));

        if ("PJ".equals(request.segmento())) {
            pjRepository.save(
                    OperacaoCreditoPj.from(
                            operacao.getIdOperacaoCredito(),
                            request.idAssociado()));
        }

        return operacao.getIdOperacaoCredito();
    }

    private void validarAgro(ContratacaoCreditoRequest request) {

        if ("AGRO".equals(request.segmento())
                && (request.areaBeneficiadaHa() == null
                || request.areaBeneficiadaHa().compareTo(BigDecimal.ZERO) <= 0)) {

            throw new NegocioException(
                    "Área beneficiada é obrigatória para operações AGRO");
        }
    }
}
