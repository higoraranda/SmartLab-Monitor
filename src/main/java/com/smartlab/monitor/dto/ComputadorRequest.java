package com.smartlab.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComputadorRequest(
        @NotBlank(message = "O patrimônio é obrigatório")
        @Size(max = 50, message = "O patrimônio deve ter no máximo 50 caracteres")
        String patrimonio,

        @NotNull(message = "O id do laboratório é obrigatório")
        Long laboratorioId
) {}
