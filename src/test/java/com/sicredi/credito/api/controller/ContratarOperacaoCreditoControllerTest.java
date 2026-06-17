package com.sicredi.credito.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultarOperacaoCreditoController.class)
class ContratarOperacaoCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private OperacaoCreditoService service;

    @Test
    @DisplayName("Deve contratar operação com sucesso")
    void deveContratarOperacaoComSucesso() throws Exception {

        ContratacaoCreditoRequest request =
                new ContratacaoCreditoRequest(
                        12345L,
                        BigDecimal.valueOf(50000),
                        "PJ",
                        "505E",
                        "1234567890",
                        null
                );

        when(service.contratar(any()))
                .thenReturn(1L);

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOperacaoCredito").value(1));
    }
}
