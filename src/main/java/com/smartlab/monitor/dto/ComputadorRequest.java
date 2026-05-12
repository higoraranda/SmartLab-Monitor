package com.smartlab.monitor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComputadorRequest(
        @NotBlank(message = "O patrimônio é obrigatório")
        @Size(max = 50, message = "O patrimônio deve ter no máximo 50 caracteres")
        String patrimonio,

        @NotNull(message = "O id do laboratório é obrigatório")
        Long laboratorioId,

        @Min(value = 1,   message = "Tempo mínimo de inatividade é 1 minuto")
        @Max(value = 120, message = "Tempo máximo de inatividade é 120 minutos")
        Integer tempoInativaMinutos  // opcional — se nulo, mantém o valor atual
) {}
