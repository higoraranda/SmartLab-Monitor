package com.smartlab.monitor.dto;

import com.smartlab.monitor.domain.MotivoDesligamento;
import java.time.LocalDateTime;

public record HistoricoDesligamentoResponse(
        Long id,
        String patrimonio,
        String laboratorioNome,
        String predioNome,
        LocalDateTime dataHora,
        MotivoDesligamento motivo
) {}
