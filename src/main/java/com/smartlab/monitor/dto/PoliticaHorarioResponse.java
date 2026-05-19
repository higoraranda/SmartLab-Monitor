package com.smartlab.monitor.dto;

public record PoliticaHorarioResponse(
        Long id,
        Long laboratorioId,
        String laboratorioNome,
        String predioNome,
        String horarioLigamento,
        String horarioDesligamento
) {}
