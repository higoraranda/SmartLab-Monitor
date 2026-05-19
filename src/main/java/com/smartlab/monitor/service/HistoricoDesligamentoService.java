package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.MotivoDesligamento;
import com.smartlab.monitor.dto.HistoricoDesligamentoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricoDesligamentoService {
    void registrar(String patrimonio, String laboratorioNome, String predioNome, MotivoDesligamento motivo);
    List<HistoricoDesligamentoResponse> buscarComFiltros(
            MotivoDesligamento motivo,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            String laboratorio,
            String patrimonio
    );
}
