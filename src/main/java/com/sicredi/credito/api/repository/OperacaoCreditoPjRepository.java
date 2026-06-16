package com.sicredi.credito.api.repository;

import com.sicredi.credito.api.entity.OperacaoCreditoPj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoCreditoPjRepository
        extends JpaRepository<OperacaoCreditoPj, Long> {
}
