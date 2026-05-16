package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.PredioRequest;
import com.smartlab.monitor.dto.PredioResponse;
import com.smartlab.monitor.mapper.PredioMapper;
import com.smartlab.monitor.repository.PredioRepository;
import com.smartlab.monitor.service.PredioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredioServiceImpl implements PredioService {

    private final PredioRepository predioRepository;
    private final PredioMapper predioMapper;

    public PredioServiceImpl(PredioRepository predioRepository, PredioMapper predioMapper) {
        this.predioRepository = predioRepository;
        this.predioMapper     = predioMapper;
    }

    @Override
    public PredioResponse criar(PredioRequest request) {
        if (predioRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe um prédio com o nome '" + request.nome() + "'.");
        }
        Predio salvo = predioRepository.save(new Predio(request.nome()));
        return predioMapper.toResponse(salvo);
    }

    @Override
    public List<PredioResponse> listarTodos() {
        return predioRepository.findAll().stream().map(predioMapper::toResponse).toList();
    }

    @Override
    public PredioResponse buscarPorId(Long id) {
        return predioMapper.toResponse(encontrarPorId(id));
    }

    @Override
    public PredioResponse atualizar(Long id, PredioRequest request) {
        Predio predio = encontrarPorId(id);

        if (!predio.getNome().equalsIgnoreCase(request.nome())
                && predioRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new IllegalArgumentException("Já existe um prédio com o nome '" + request.nome() + "'.");
        }

        predio.atualizarNome(request.nome());
        return predioMapper.toResponse(predioRepository.save(predio));
    }

    @Override
    public void remover(Long id) {
        if (!predioRepository.existsById(id)) {
            throw new IllegalArgumentException("Prédio não encontrado: " + id);
        }
        predioRepository.deleteById(id);
    }

    @Override
    public Predio encontrarPorId(Long id) {
        return predioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prédio não encontrado: " + id));
    }
}
