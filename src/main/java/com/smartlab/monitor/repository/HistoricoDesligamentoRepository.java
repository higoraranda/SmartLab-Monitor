package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.HistoricoDesligamento;
import com.smartlab.monitor.domain.MotivoDesligamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricoDesligamentoRepository extends JpaRepository<HistoricoDesligamento, Long> {

    @Query("SELECT h FROM HistoricoDesligamento h WHERE " +
           "(:motivo IS NULL OR h.motivo = :motivo) AND " +
           "(:dataInicio IS NULL OR h.dataHora >= :dataInicio) AND " +
           "(:dataFim IS NULL OR h.dataHora <= :dataFim) AND " +
           "(:laboratorio IS NULL OR LOWER(h.laboratorioNome) LIKE LOWER(CONCAT('%', :laboratorio, '%'))) AND " +
           "(:patrimonio IS NULL OR LOWER(h.patrimonio) LIKE LOWER(CONCAT('%', :patrimonio, '%'))) " +
           "ORDER BY h.dataHora DESC")
    List<HistoricoDesligamento> buscarComFiltros(
            @Param("motivo")      MotivoDesligamento motivo,
            @Param("dataInicio")  LocalDateTime dataInicio,
            @Param("dataFim")     LocalDateTime dataFim,
            @Param("laboratorio") String laboratorio,
            @Param("patrimonio")  String patrimonio
    );
}
