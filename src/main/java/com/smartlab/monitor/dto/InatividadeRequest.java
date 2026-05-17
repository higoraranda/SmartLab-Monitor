package com.smartlab.monitor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InatividadeRequest(
        @NotNull(message = "O campo 'tempoOciosoGlobal' é obrigatório.")
        @Min(value = 10, message = "O tempo de inatividade deve ser de no mínimo 10 segundos.")
        Integer tempoOciosoGlobal
) {}
