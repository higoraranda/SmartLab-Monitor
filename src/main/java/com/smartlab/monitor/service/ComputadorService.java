package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.dto.ComputadorRequest;
import com.smartlab.monitor.dto.ComputadorResponse;
import com.smartlab.monitor.repository.ComputadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputadorService {

    private final ComputadorRepository computadorRepository;
    private final LaboratorioService laboratorioService;

    public ComputadorService(ComputadorRepository computadorRepository, LaboratorioService laboratorioService) {
        this.computadorRepository = computadorRepository;
        this.laboratorioService   = laboratorioService;
    }

    public ComputadorResponse criar(ComputadorRequest request) {
        if (computadorRepository.existsByPatrimonioIgnoreCase(request.patrimonio())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o patrimônio '" + request.patrimonio() + "'.");
        }

        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        Computador pc   = new Computador(request.patrimonio(), lab);
        return toResponse(computadorRepository.save(pc));
    }

    public List<ComputadorResponse> listarTodos() {
        return computadorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ComputadorResponse buscarPorId(Long id) {
        return toResponse(encontrarPorId(id));
    }

    public ComputadorResponse atualizar(Long id, ComputadorRequest request) {
        Computador pc = encontrarPorId(id);

        if (!pc.getPatrimonio().equalsIgnoreCase(request.patrimonio())
                && computadorRepository.existsByPatrimonioIgnoreCase(request.patrimonio())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o patrimônio '" + request.patrimonio() + "'.");
        }

        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        pc.atualizarDados(request.patrimonio(), lab);
        return toResponse(computadorRepository.save(pc));
    }

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

    private ComputadorResponse toResponse(Computador pc) {
        return new ComputadorResponse(
                pc.getId(),
                pc.getPatrimonio(),
                pc.getLaboratorio().getId(),
                pc.getLaboratorio().getNome(),
                pc.getLaboratorio().getPredio().getNome()
        );
    }
}
