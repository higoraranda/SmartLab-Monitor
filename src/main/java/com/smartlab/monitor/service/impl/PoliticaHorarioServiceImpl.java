package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.PoliticaHorario;
import com.smartlab.monitor.dto.PoliticaHorarioRequest;
import com.smartlab.monitor.dto.PoliticaHorarioResponse;
import com.smartlab.monitor.mapper.PoliticaHorarioMapper;
import com.smartlab.monitor.repository.PoliticaHorarioRepository;
import com.smartlab.monitor.service.LaboratorioService;
import com.smartlab.monitor.service.PoliticaHorarioService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class PoliticaHorarioServiceImpl implements PoliticaHorarioService {

    private final PoliticaHorarioRepository politicaRepository;
    private final LaboratorioService laboratorioService;
    private final PoliticaHorarioMapper mapper;

    public PoliticaHorarioServiceImpl(PoliticaHorarioRepository politicaRepository,
                                      LaboratorioService laboratorioService,
                                      PoliticaHorarioMapper mapper) {
        this.politicaRepository = politicaRepository;
        this.laboratorioService = laboratorioService;
        this.mapper             = mapper;
    }

    @Override
    public PoliticaHorarioResponse criar(PoliticaHorarioRequest request) {
        validarOrdem(request.horarioLigamento(), request.horarioDesligamento());
        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        PoliticaHorario politica = new PoliticaHorario(lab, request.horarioLigamento(), request.horarioDesligamento());
        return mapper.toResponse(politicaRepository.save(politica));
    }

    @Override
    public List<PoliticaHorarioResponse> listarTodos() {
        return politicaRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PoliticaHorarioResponse> listarPorLaboratorio(Long laboratorioId) {
        return politicaRepository.findByLaboratorioId(laboratorioId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public PoliticaHorarioResponse buscarPorId(Long id) {
        return mapper.toResponse(encontrarPorId(id));
    }

    @Override
    public PoliticaHorarioResponse atualizar(Long id, PoliticaHorarioRequest request) {
        validarOrdem(request.horarioLigamento(), request.horarioDesligamento());
        PoliticaHorario politica = encontrarPorId(id);
        Laboratorio lab = laboratorioService.encontrarPorId(request.laboratorioId());
        politica.atualizar(lab, request.horarioLigamento(), request.horarioDesligamento());
        return mapper.toResponse(politicaRepository.save(politica));
    }

    @Override
    public void remover(Long id) {
        if (!politicaRepository.existsById(id)) {
            throw new IllegalArgumentException("Política não encontrada: " + id);
        }
        politicaRepository.deleteById(id);
    }

    private PoliticaHorario encontrarPorId(Long id) {
        return politicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Política não encontrada: " + id));
    }

    private void validarOrdem(String ligamento, String desligamento) {
        LocalTime tLiga  = LocalTime.parse(ligamento);
        LocalTime tDesliga = LocalTime.parse(desligamento);
        if (!tDesliga.isAfter(tLiga)) {
            throw new IllegalArgumentException(
                    "O horário de desligamento (" + desligamento + ") deve ser posterior ao de ligamento (" + ligamento + ").");
        }
    }
}
