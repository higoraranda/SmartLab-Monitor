package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    boolean existsByNomeIgnoreCaseAndPredioId(String nome, Long predioId);
}
