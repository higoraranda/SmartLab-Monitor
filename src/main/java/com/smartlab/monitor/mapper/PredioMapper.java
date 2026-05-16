package com.smartlab.monitor.mapper;

import com.smartlab.monitor.domain.Predio;
import com.smartlab.monitor.dto.PredioResponse;
import org.springframework.stereotype.Component;

@Component
public class PredioMapper {

    public PredioResponse toResponse(Predio predio) {
        return new PredioResponse(
                predio.getId(),
                predio.getNome(),
                predio.getLaboratorios().size()
        );
    }
}
