package com.sicredi.credito.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "operacoes_credito_pj")
public class OperacaoCreditoPj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idOperacaoCredito;

    private Long idAssociado;

    public static OperacaoCreditoPj from(
            Long idOperacaoCredito,
            Long idAssociado) {

        OperacaoCreditoPj vinculo = new OperacaoCreditoPj();

        vinculo.setIdOperacaoCredito(idOperacaoCredito);
        vinculo.setIdAssociado(idAssociado);

        return vinculo;
    }
}
