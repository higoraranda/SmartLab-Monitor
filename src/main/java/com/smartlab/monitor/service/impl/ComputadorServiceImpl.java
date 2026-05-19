package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.MotivoDesligamento;
import com.smartlab.monitor.domain.StatusComputador;
import com.smartlab.monitor.dto.ComputadorRequest;
import com.smartlab.monitor.dto.ComputadorResponse;
import com.smartlab.monitor.mapper.ComputadorMapper;
import com.smartlab.monitor.repository.ComputadorRepository;
import com.smartlab.monitor.service.ComputadorService;
import com.smartlab.monitor.service.HistoricoDesligamentoService;
import com.smartlab.monitor.service.LaboratorioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComputadorServiceImpl implements ComputadorService {

    private static final Logger log = LoggerFactory.getLogger(ComputadorServiceImpl.class);

    private final ComputadorRepository computadorRepository;
    private final LaboratorioService laboratorioService;
    private final ComputadorMapper computadorMapper;
    private final HistoricoDesligamentoService historicoService;

    public ComputadorServiceImpl(ComputadorRepository computadorRepository,
                                 LaboratorioService laboratorioService,
                                 ComputadorMapper computadorMapper,
                                 HistoricoDesligamentoService historicoService) {
        this.computadorRepository = computadorRepository;
        this.laboratorioService   = laboratorioService;
        this.computadorMapper     = computadorMapper;
        this.historicoService     = historicoService;
    }

    @Override
    public ComputadorResponse criar(ComputadorRequest request) {
        if (computadorRepository.existsByPatrimonioIgnoreCase(request.patrimonio())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o patrimônio '" + request.patrimonio() + "'.");
        }

        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        Computador pc   = new Computador(request.patrimonio(), lab);
        return computadorMapper.toResponse(computadorRepository.save(pc));
    }

    @Override
    public List<ComputadorResponse> listarTodos() {
        return computadorRepository.findAll().stream().map(computadorMapper::toResponse).toList();
    }

    @Override
    public ComputadorResponse buscarPorId(Long id) {
        return computadorMapper.toResponse(encontrarPorId(id));
    }

    @Override
    public ComputadorResponse atualizar(Long id, ComputadorRequest request) {
        Computador pc = encontrarPorId(id);

        if (!pc.getPatrimonio().equalsIgnoreCase(request.patrimonio())
                && computadorRepository.existsByPatrimonioIgnoreCase(request.patrimonio())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o patrimônio '" + request.patrimonio() + "'.");
        }

        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        pc.atualizarDados(request.patrimonio(), lab);
        return computadorMapper.toResponse(computadorRepository.save(pc));
    }

    @Override
    @Transactional
    public ComputadorResponse atualizarStatus(Long id, StatusComputador status, MotivoDesligamento motivo) {
        Computador pc = encontrarPorId(id);
        pc.setStatus(status);
        Computador salvo = computadorRepository.save(pc);

        if (status == StatusComputador.DESLIGADO && motivo != null) {
            try {
                Laboratorio lab = pc.getLaboratorio();
                historicoService.registrar(
                        pc.getPatrimonio(),
                        lab.getNome(),
                        lab.getPredio().getNome(),
                        motivo
                );
                log.info("[Histórico] Registrado — PC: {} | Lab: {} | Motivo: {}",
                        pc.getPatrimonio(), lab.getNome(), motivo);
            } catch (Exception e) {
                log.error("[Histórico] Falha ao registrar desligamento do PC {}: {}", id, e.getMessage(), e);
            }
        }

        return computadorMapper.toResponse(salvo);
    }

    @Override
    public void remover(Long id) {
        if (!computadorRepository.existsById(id)) {
            throw new IllegalArgumentException("Computador não encontrado: " + id);
        }
        computadorRepository.deleteById(id);
    }

    private Computador encontrarPorId(Long id) {
        return computadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Computador não encontrado: " + id));
    }
}
