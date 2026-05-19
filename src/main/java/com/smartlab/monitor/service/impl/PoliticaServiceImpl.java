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
}
