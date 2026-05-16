package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.PredioRequest;
import com.smartlab.monitor.dto.PredioResponse;

import java.util.List;

public interface PredioService {
    PredioResponse criar(PredioRequest request);
    List<PredioResponse> listarTodos();
    PredioResponse buscarPorId(Long id);
    PredioResponse atualizar(Long id, PredioRequest request);
    void remover(Long id);

    /** Uso interno por outros services que precisam da entidade. */
    Predio encontrarPorId(Long id);
}
