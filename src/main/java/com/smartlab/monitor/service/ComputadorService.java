package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.StatusComputador;
import com.smartlab.monitor.dto.ComputadorRequest;
import com.smartlab.monitor.dto.ComputadorResponse;
import com.smartlab.monitor.repository.ComputadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComputadorService {

    private final ComputadorRepository computadorRepository;
    private final LaboratorioService   laboratorioService;

    public ComputadorService(ComputadorRepository computadorRepository,
                             LaboratorioService laboratorioService) {
        this.computadorRepository = computadorRepository;
        this.laboratorioService   = laboratorioService;
    }

    // -------------------------------------------------------------------------
    // CRUD básico
    // -------------------------------------------------------------------------

    public ComputadorResponse criar(ComputadorRequest request) {
        if (computadorRepository.existsByPatrimonioIgnoreCase(request.patrimonio())) {
            throw new IllegalArgumentException(
                    "Já existe um computador com o patrimônio '" + request.patrimonio() + "'.");
        }
        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        Computador  pc  = new Computador(request.patrimonio(), lab);
        if (request.tempoInativaMinutos() != null) {
            pc.atualizarTempo(request.tempoInativaMinutos());
        }
        return toResponse(computadorRepository.save(pc));
    }

    public List<ComputadorResponse> listarTodos() {
        return computadorRepository.findAll().stream().map(this::toResponse).toList();
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
        if (request.tempoInativaMinutos() != null) {
            pc.atualizarTempo(request.tempoInativaMinutos());
        }
        return toResponse(computadorRepository.save(pc));
    }

    public void remover(Long id) {
        if (!computadorRepository.existsById(id)) {
            throw new IllegalArgumentException("Computador não encontrado: " + id);
        }
        computadorRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Controle de inatividade
    // -------------------------------------------------------------------------

    /** Chamado pelo frontend quando detecta movimento de mouse. */
    public ComputadorResponse registrarAtividade(Long id) {
        Computador pc = encontrarPorId(id);
        if (pc.getStatus() == StatusComputador.DESLIGADO) {
            throw new IllegalStateException("Computador está desligado. Ligue-o primeiro.");
        }
        pc.registrarAtividade();
        return toResponse(computadorRepository.save(pc));
    }

    /** Liga a máquina e marca o instante atual como primeira atividade. */
    public ComputadorResponse ligar(Long id) {
        Computador pc = encontrarPorId(id);
        pc.registrarAtividade();
        return toResponse(computadorRepository.save(pc));
    }

    /** Desliga a máquina imediatamente. */
    public ComputadorResponse desligar(Long id) {
        Computador pc = encontrarPorId(id);
        pc.desligar();
        return toResponse(computadorRepository.save(pc));
    }

    /** Chamado pelo scheduler a cada 30 segundos para verificar inatividade. */
    public void verificarInatividade() {
        LocalDateTime agora = LocalDateTime.now();

        for (Computador pc : computadorRepository.findAll()) {

            if (pc.getStatus() == StatusComputador.DESLIGADO) continue;
            if (pc.getUltimaAtividade() == null)              continue;

            long minutosParados = java.time.Duration
                    .between(pc.getUltimaAtividade(), agora)
                    .toMinutes();

            if (pc.getStatus() == StatusComputador.EM_USO
                    && minutosParados >= pc.getTempoInativaMinutos()) {
                // passou do tempo de inatividade → entra em estado ocioso (aviso)
                pc.marcarOcioso();
                computadorRepository.save(pc);

            } else if (pc.getStatus() == StatusComputador.OCIOSO
                    && minutosParados >= pc.getTempoInativaMinutos() + 1) {
                // ficou 1 minuto extra no estado ocioso → desliga
                pc.desligar();
                computadorRepository.save(pc);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    public Computador encontrarPorId(Long id) {
        return computadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Computador não encontrado: " + id));
    }

    private ComputadorResponse toResponse(Computador pc) {
        return new ComputadorResponse(
                pc.getId(),
                pc.getPatrimonio(),
                pc.getLaboratorio().getId(),
                pc.getLaboratorio().getNome(),
                pc.getLaboratorio().getPredio().getNome(),
                pc.getStatus(),
                pc.getUltimaAtividade(),
                pc.getTempoInativaMinutos()
        );
    }
}
