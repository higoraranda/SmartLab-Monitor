package com.smartlab.monitor.dto;

import java.util.List;

public record LaboratorioResponse(Long id, String nome, Long predioId, String predioNome, int totalComputadores, int tempoOciosoGlobal, List<String> horariosFixos, List<String> horariosLigamento) {}
