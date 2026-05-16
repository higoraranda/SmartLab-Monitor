package com.smartlab.monitor.service;

import com.smartlab.monitor.dto.LaboratorioResponse;

public interface PoliticaService {
    LaboratorioResponse definirInatividade(Long labId, int tempoOciosoGlobal);
    LaboratorioResponse adicionarHorario(Long labId, String horario);
    LaboratorioResponse limparHorarios(Long labId);
}
