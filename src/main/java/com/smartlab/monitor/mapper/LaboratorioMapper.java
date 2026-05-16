package com.smartlab.monitor.mapper;

import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.dto.LaboratorioResponse;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioMapper {

    public LaboratorioResponse toResponse(Laboratorio lab) {
        return new LaboratorioResponse(
                lab.getId(),
                lab.getNome(),
                lab.getPredio().getId(),
                lab.getPredio().getNome(),
                lab.getComputadores().size(),
                lab.getTempoOciosoGlobal(),
                lab.getHorariosFixos()
        );
    }
}
