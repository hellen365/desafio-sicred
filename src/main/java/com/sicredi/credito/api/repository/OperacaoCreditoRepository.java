package com.sicredi.credito.api.repository;


import com.sicredi.credito.api.entity.OperacaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoCreditoRepository
        extends JpaRepository<OperacaoCredito, Long> {
}
