package com.sicredi.credito.api.client;

import com.sicredi.credito.api.dto.PermiteContratacaoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class ProdutoCreditoClient {

    private final RestClient restClient;

    public ProdutoCreditoClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public boolean permiteContratacao(
            String codigoProduto,
            String segmento,
            BigDecimal valorOperacao) {

        PermiteContratacaoResponse response =
                restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("https")
                                .host("desafio-credito-sicredi.wiremockapi.cloud")
                                .path("/produtos-credito/{codigo}/permite-contratacao")
                                .queryParam("segmento", segmento)
                                .queryParam("valorFinanciado", valorOperacao)
                                .build(codigoProduto))
                        .retrieve()
                        .body(PermiteContratacaoResponse.class);

        return response.permiteContratar();
    }
}
