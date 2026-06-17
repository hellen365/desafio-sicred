package com.sicredi.credito.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.credito.api.dto.ConsultaOperacaoCreditoResponse;
import com.sicredi.credito.api.dto.ContratacaoCreditoRequest;
import com.sicredi.credito.application.service.OperacaoCreditoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultarOperacaoCreditoController.class)
class ConsultarOperacaoCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OperacaoCreditoService service;

    @Test
    @DisplayName("Deve consultar operação por id")
    void deveConsultarOperacaoPorId() throws Exception {

        ConsultaOperacaoCreditoResponse response =
                new ConsultaOperacaoCreditoResponse(
                        1L,
                        12345L,
                        BigDecimal.valueOf(50000),
                        "PJ",
                        "505E",
                        "1234567890",
                        null,
                        LocalDateTime.now()
                );

        when(service.consultar(1L))
                .thenReturn(response);

        mockMvc.perform(get("/operacoes-credito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOperacaoCredito").value(1))
                .andExpect(jsonPath("$.idAssociado").value(12345))
                .andExpect(jsonPath("$.segmento").value("PJ"))
                .andExpect(jsonPath("$.codigoProdutoCredito").value("505E"));
    }
}
