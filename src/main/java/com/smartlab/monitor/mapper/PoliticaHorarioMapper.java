package com.smartlab.monitor.mapper;

import com.smartlab.monitor.domain.PoliticaHorario;
import com.smartlab.monitor.dto.PoliticaHorarioResponse;
import org.springframework.stereotype.Component;

@Component
public class PoliticaHorarioMapper {

    public PoliticaHorarioResponse toResponse(PoliticaHorario p) {
        return new PoliticaHorarioResponse(
                p.getId(),
                p.getLaboratorio().getId(),
                p.getLaboratorio().getNome(),
                p.getLaboratorio().getPredio().getNome(),
                p.getHorarioLigamento(),
                p.getHorarioDesligamento()
        );
    }
}
