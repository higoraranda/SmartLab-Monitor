package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.MotivoDesligamento;
import com.smartlab.monitor.domain.StatusComputador;
import com.smartlab.monitor.dto.ComputadorRequest;
import com.smartlab.monitor.dto.ComputadorResponse;

import java.util.List;

public interface ComputadorService {
    ComputadorResponse criar(ComputadorRequest request);
    List<ComputadorResponse> listarTodos();
    ComputadorResponse buscarPorId(Long id);
    ComputadorResponse atualizar(Long id, ComputadorRequest request);
    ComputadorResponse atualizarStatus(Long id, StatusComputador status, MotivoDesligamento motivo);
    void remover(Long id);
}
