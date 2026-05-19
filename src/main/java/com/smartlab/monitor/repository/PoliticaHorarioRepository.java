package com.smartlab.monitor.repository;

import com.smartlab.monitor.domain.PoliticaHorario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoliticaHorarioRepository extends JpaRepository<PoliticaHorario, Long> {
    List<PoliticaHorario> findByLaboratorioId(Long laboratorioId);
    List<PoliticaHorario> findByHorarioDesligamento(String horario);
}
