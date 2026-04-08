package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.Computador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputadorRepository extends JpaRepository<Computador, Long> {
    boolean existsByPatrimonioIgnoreCase(String patrimonio);
}
