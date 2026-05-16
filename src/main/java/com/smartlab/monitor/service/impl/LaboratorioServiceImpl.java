package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.LaboratorioRequest;
import com.smartlab.monitor.dto.LaboratorioResponse;
import com.smartlab.monitor.mapper.LaboratorioMapper;
import com.smartlab.monitor.repository.LaboratorioRepository;
import com.smartlab.monitor.service.LaboratorioService;
import com.smartlab.monitor.service.PredioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratorioServiceImpl implements LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final PredioService predioService;
    private final LaboratorioMapper laboratorioMapper;

    public LaboratorioServiceImpl(LaboratorioRepository laboratorioRepository,
                                  PredioService predioService,
                                  LaboratorioMapper laboratorioMapper) {
        this.laboratorioRepository = laboratorioRepository;
        this.predioService         = predioService;
        this.laboratorioMapper     = laboratorioMapper;
    }

    @Override
    public LaboratorioResponse criar(LaboratorioRequest request) {
        Predio predio = predioService.encontrarPorId(request.predioId());

        if (laboratorioRepository.existsByNomeIgnoreCaseAndPredioId(request.nome(), request.predioId())) {
            throw new IllegalArgumentException(
                    "Já existe um laboratório com o nome '" + request.nome() + "' neste prédio.");
        }

        Laboratorio salvo = laboratorioRepository.save(new Laboratorio(request.nome(), predio));
        return laboratorioMapper.toResponse(salvo);
    }

    @Override
    public List<LaboratorioResponse> listarTodos() {
        return laboratorioRepository.findAll().stream().map(laboratorioMapper::toResponse).toList();
    }

    @Override
    public LaboratorioResponse buscarPorId(Long id) {
        return laboratorioMapper.toResponse(encontrarPorId(id));
    }

    @Override
    public LaboratorioResponse atualizar(Long id, LaboratorioRequest request) {
        Laboratorio lab = encontrarPorId(id);
        Predio predio   = predioService.encontrarPorId(request.predioId());

        boolean nomeIgual   = lab.getNome().equalsIgnoreCase(request.nome());
        boolean predioIgual = lab.getPredio().getId().equals(request.predioId());

        if ((!nomeIgual || !predioIgual)
                && laboratorioRepository.existsByNomeIgnoreCaseAndPredioId(request.nome(), request.predioId())) {
            throw new IllegalArgumentException(
                    "Já existe um laboratório com o nome '" + request.nome() + "' neste prédio.");
        }

        lab.atualizarDados(request.nome(), predio);
        return laboratorioMapper.toResponse(laboratorioRepository.save(lab));
    }

    @Override
    public void remover(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            throw new IllegalArgumentException("Laboratório não encontrado: " + id);
        }
        laboratorioRepository.deleteById(id);
    }

    @Override
    public Laboratorio encontrarPorId(Long id) {
        return laboratorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Laboratório não encontrado: " + id));
    }
}
