package com.smartlab.monitor.service;

import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.PredioRequest;
import com.smartlab.monitor.dto.PredioResponse;
import com.smartlab.monitor.repository.PredioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredioService {

    private final PredioRepository predioRepository;

    public PredioService(PredioRepository predioRepository) {
        this.predioRepository = predioRepository;
    }

    public PredioResponse criar(PredioRequest request) {
        if (predioRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe um prédio com o nome '" + request.nome() + "'.");
        }
        Predio predio = new Predio(request.nome());
        Predio salvo = predioRepository.save(predio);
        return toResponse(salvo);
    }

    public List<PredioResponse> listarTodos() {
        return predioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PredioResponse buscarPorId(Long id) {
        Predio predio = encontrarPorId(id);
        return toResponse(predio);
    }

    public PredioResponse atualizar(Long id, PredioRequest request) {
        Predio predio = encontrarPorId(id);

        if (!predio.getNome().equalsIgnoreCase(request.nome())
                && predioRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe um prédio com o nome '" + request.nome() + "'.");
        }

        predio.atualizarNome(request.nome());
        Predio atualizado = predioRepository.save(predio);
        return toResponse(atualizado);
    }

    public void remover(Long id) {
        if (!predioRepository.existsById(id)) {
            throw new IllegalArgumentException("Prédio não encontrado: " + id);
        }
        predioRepository.deleteById(id);
    }

    // Método reutilizado internamente por outros services
    public Predio encontrarPorId(Long id) {
        return predioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prédio não encontrado: " + id));
    }

    private PredioResponse toResponse(Predio predio) {
        return new PredioResponse(
                predio.getId(),
                predio.getNome(),
                predio.getLaboratorios().size()
        );
    }
}
