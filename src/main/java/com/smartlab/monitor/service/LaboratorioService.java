package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.LaboratorioRequest;
import com.smartlab.monitor.dto.LaboratorioResponse;
import com.smartlab.monitor.repository.LaboratorioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final PredioService predioService;

    public LaboratorioService(LaboratorioRepository laboratorioRepository, PredioService predioService) {
        this.laboratorioRepository = laboratorioRepository;
        this.predioService         = predioService;
    }

    public LaboratorioResponse criar(LaboratorioRequest request) {
        Predio predio = predioService.encontrarPorId(request.predioId());

        if (laboratorioRepository.existsByNomeIgnoreCaseAndPredioId(request.nome(), request.predioId())) {
            throw new IllegalArgumentException(
                    "Já existe um laboratório com o nome '" + request.nome() + "' neste prédio.");
        }

        Laboratorio lab = new Laboratorio(request.nome(), predio);
        Laboratorio salvo = laboratorioRepository.save(lab);
        return toResponse(salvo);
    }

    public List<LaboratorioResponse> listarTodos() {
        return laboratorioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LaboratorioResponse buscarPorId(Long id) {
        return toResponse(encontrarPorId(id));
    }

    public LaboratorioResponse atualizar(Long id, LaboratorioRequest request) {
        Laboratorio lab   = encontrarPorId(id);
        Predio predio     = predioService.encontrarPorId(request.predioId());

        boolean nomeIgual  = lab.getNome().equalsIgnoreCase(request.nome());
        boolean predioIgual = lab.getPredio().getId().equals(request.predioId());

        if ((!nomeIgual || !predioIgual)
                && laboratorioRepository.existsByNomeIgnoreCaseAndPredioId(request.nome(), request.predioId())) {
            throw new IllegalArgumentException(
                    "Já existe um laboratório com o nome '" + request.nome() + "' neste prédio.");
        }

        lab.atualizarDados(request.nome(), predio);
        return toResponse(laboratorioRepository.save(lab));
    }

    public void remover(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            throw new IllegalArgumentException("Laboratório não encontrado: " + id);
        }
        laboratorioRepository.deleteById(id);
    }

    // Método reutilizado internamente por outros services
    public Laboratorio encontrarPorId(Long id) {
        return laboratorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Laboratório não encontrado: " + id));
    }

    private LaboratorioResponse toResponse(Laboratorio lab) {
        return new LaboratorioResponse(
                lab.getId(),
                lab.getNome(),
                lab.getPredio().getId(),
                lab.getPredio().getNome(),
                lab.getComputadores().size()
        );
    }
}
