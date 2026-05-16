package com.smartlab.monitor.dto;

import com.smartlab.monitor.domain.StatusComputador;

public record ComputadorResponse(
        Long id,
        String patrimonio,
        Long laboratorioId,
        String laboratorioNome,
        String predioNome,
        StatusComputador status
) {}
