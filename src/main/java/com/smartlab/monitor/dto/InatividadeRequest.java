package com.smartlab.monitor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InatividadeRequest(
        @NotNull(message = "O campo 'tempoOciosoGlobal' é obrigatório.")
        @Min(value = 300, message = "O tempo de inatividade deve ser de no mínimo 300 segundos (5 minutos).")
        Integer tempoOciosoGlobal
) {}
