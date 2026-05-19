package com.smartlab.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PoliticaHorarioRequest(
        @NotNull(message = "O campo 'laboratorioId' é obrigatório.")
        Long laboratorioId,

        @NotBlank(message = "O campo 'horarioLigamento' é obrigatório.")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário de ligamento inválido. Use o formato HH:mm.")
        String horarioLigamento,

        @NotBlank(message = "O campo 'horarioDesligamento' é obrigatório.")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário de desligamento inválido. Use o formato HH:mm.")
        String horarioDesligamento
) {}
