package com.smartlab.monitor.service;

import com.smartlab.monitor.dto.PoliticaHorarioRequest;
import com.smartlab.monitor.dto.PoliticaHorarioResponse;

import java.util.List;

public interface PoliticaHorarioService {
    PoliticaHorarioResponse criar(PoliticaHorarioRequest request);
    List<PoliticaHorarioResponse> listarTodos();
    List<PoliticaHorarioResponse> listarPorLaboratorio(Long laboratorioId);
    PoliticaHorarioResponse buscarPorId(Long id);
    PoliticaHorarioResponse atualizar(Long id, PoliticaHorarioRequest request);
    void remover(Long id);
}
