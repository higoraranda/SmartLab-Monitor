package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.StatusComputador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputadorRepository extends JpaRepository<Computador, Long> {
    boolean existsByPatrimonioIgnoreCase(String patrimonio);
    List<Computador> findByLaboratorioAndStatusIn(Laboratorio laboratorio, List<StatusComputador> statuses);
}
