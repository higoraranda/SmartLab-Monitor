package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.dto.LaboratorioRequest;
import com.smartlab.monitor.dto.LaboratorioResponse;

import java.util.List;

public interface LaboratorioService {
    LaboratorioResponse criar(LaboratorioRequest request);
    List<LaboratorioResponse> listarTodos();
    LaboratorioResponse buscarPorId(Long id);
    LaboratorioResponse atualizar(Long id, LaboratorioRequest request);
    void remover(Long id);

    /** Uso interno por outros services que precisam da entidade. */
    Laboratorio encontrarPorId(Long id);
}
