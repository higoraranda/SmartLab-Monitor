package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.HistoricoDesligamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoDesligamentoRepository extends JpaRepository<HistoricoDesligamento, Long> {

    List<HistoricoDesligamento> findByLaboratorioNomeIgnoreCaseOrderByDataHoraDesc(String laboratorioNome);

    List<HistoricoDesligamento> findAllByOrderByDataHoraDesc();
}
