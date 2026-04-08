package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.Predio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredioRepository extends JpaRepository<Predio, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
