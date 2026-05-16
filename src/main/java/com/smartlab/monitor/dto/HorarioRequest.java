package com.smartlab.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record HorarioRequest(
        @NotBlank(message = "O campo 'horario' é obrigatório.")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato HH:mm (ex: 08:30).")
        String horario
) {}
