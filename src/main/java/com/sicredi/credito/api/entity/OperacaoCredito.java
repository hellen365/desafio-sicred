package com.sicredi.credito.api.entity;

import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "operacao_credito")
public class OperacaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacaoCredito;

    private Long idAssociado;

    private BigDecimal valorOperacao;

    private String segmento;

    private String codigoProdutoCredito;

    private String codigoConta;

    private BigDecimal areaBeneficiadaHa;

    private LocalDateTime dataHoraContratacao;

    public static OperacaoCredito from(
            ContratacaoCreditoRequest request) {

        OperacaoCredito operacao = new OperacaoCredito();

        operacao.setIdAssociado(request.idAssociado());
        operacao.setValorOperacao(request.valorOperacao());
        operacao.setSegmento(request.segmento());
        operacao.setCodigoProdutoCredito(request.codigoProdutoCredito());
        operacao.setCodigoConta(request.codigoConta());
        operacao.setAreaBeneficiadaHa(request.areaBeneficiadaHa());
        operacao.setDataHoraContratacao(LocalDateTime.now());

        return operacao;
    }
}
