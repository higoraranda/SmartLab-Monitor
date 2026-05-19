package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.HistoricoDesligamento;
import com.smartlab.monitor.domain.MotivoDesligamento;
import com.smartlab.monitor.dto.HistoricoDesligamentoResponse;
import com.smartlab.monitor.repository.HistoricoDesligamentoRepository;
import com.smartlab.monitor.service.HistoricoDesligamentoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoDesligamentoServiceImpl implements HistoricoDesligamentoService {

    private final HistoricoDesligamentoRepository repository;

    public HistoricoDesligamentoServiceImpl(HistoricoDesligamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrar(String patrimonio, String laboratorioNome,
                          String predioNome, MotivoDesligamento motivo) {
        repository.save(new HistoricoDesligamento(patrimonio, laboratorioNome, predioNome, motivo));
    }

    @Override
    public List<HistoricoDesligamentoResponse> buscarComFiltros(
            MotivoDesligamento motivo, LocalDateTime dataInicio,
            LocalDateTime dataFim, String laboratorio, String patrimonio) {

        return repository.buscarComFiltros(motivo, dataInicio, dataFim, laboratorio, patrimonio)
                .stream()
                .map(h -> new HistoricoDesligamentoResponse(
                        h.getId(), h.getPatrimonio(), h.getLaboratorioNome(),
                        h.getPredioNome(), h.getDataHora(), h.getMotivo()))
                .toList();
    }
}
