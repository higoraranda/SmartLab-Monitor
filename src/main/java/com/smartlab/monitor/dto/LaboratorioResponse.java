package com.smartlab.monitor.dto;

public record LaboratorioResponse(Long id, String nome, Long predioId, String predioNome, int totalComputadores) {}
