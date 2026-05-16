package com.smartlab.monitor.service.impl;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.dto.LaboratorioResponse;
import com.smartlab.monitor.mapper.LaboratorioMapper;
import com.smartlab.monitor.repository.LaboratorioRepository;
import com.smartlab.monitor.service.LaboratorioService;
import com.smartlab.monitor.service.PoliticaService;
import org.springframework.stereotype.Service;

@Service
public class PoliticaServiceImpl implements PoliticaService {

    private final LaboratorioRepository laboratorioRepository;
    private final LaboratorioService laboratorioService;
    private final LaboratorioMapper laboratorioMapper;

    public PoliticaServiceImpl(LaboratorioRepository laboratorioRepository,
                               LaboratorioService laboratorioService,
                               LaboratorioMapper laboratorioMapper) {
        this.laboratorioRepository = laboratorioRepository;
        this.laboratorioService    = laboratorioService;
        this.laboratorioMapper     = laboratorioMapper;
    }

    @Override
    public LaboratorioResponse definirInatividade(Long labId, int tempoOciosoGlobal) {
        Laboratorio lab = laboratorioService.encontrarPorId(labId);
        lab.setTempoOciosoGlobal(tempoOciosoGlobal);
        return laboratorioMapper.toResponse(laboratorioRepository.save(lab));
    }

    @Override
    public LaboratorioResponse adicionarHorario(Long labId, String horario) {
        Laboratorio lab = laboratorioService.encontrarPorId(labId);
        if (lab.getHorariosFixos().contains(horario)) {
            throw new IllegalArgumentException("Este horário já está cadastrado neste laboratório.");
        }
        lab.getHorariosFixos().add(horario);
        return laboratorioMapper.toResponse(laboratorioRepository.save(lab));
    }

    @Override
    public LaboratorioResponse limparHorarios(Long labId) {
        Laboratorio lab = laboratorioService.encontrarPorId(labId);
        lab.getHorariosFixos().clear();
        return laboratorioMapper.toResponse(laboratorioRepository.save(lab));
    }
}
