package com.sicredi.credito.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperacaoCreditoPjTest {

    @Test
    void from_shouldPopulateFields() {
        Long idOperacao = 123L;
        Long idAssociado = 456L;

        OperacaoCreditoPj obj = OperacaoCreditoPj.from(idOperacao, idAssociado);

        assertNull(obj.getId(), "id deve ser nulo para entidade recém-criada");
        assertEquals(idOperacao, obj.getIdOperacaoCredito());
        assertEquals(idAssociado, obj.getIdAssociado());
    }
}
