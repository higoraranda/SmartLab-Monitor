package com.smartlab.monitor.dto;

import com.smartlab.monitor.domain.StatusComputador;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull(message = "O campo 'status' é obrigatório.")
        StatusComputador status
) {}
