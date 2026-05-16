package com.smartlab.monitor.mapper;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.dto.ComputadorResponse;
import org.springframework.stereotype.Component;

@Component
public class ComputadorMapper {

    public ComputadorResponse toResponse(Computador pc) {
        return new ComputadorResponse(
                pc.getId(),
                pc.getPatrimonio(),
                pc.getLaboratorio().getId(),
                pc.getLaboratorio().getNome(),
                pc.getLaboratorio().getPredio().getNome(),
                pc.getStatus()
        );
    }
}
