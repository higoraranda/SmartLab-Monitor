package com.smartlab.monitor.scheduler;

import com.smartlab.monitor.domain.Computador;
import com.smartlab.monitor.domain.Laboratorio;
import com.smartlab.monitor.domain.PoliticaHorario;
import com.smartlab.monitor.domain.StatusComputador;
import com.smartlab.monitor.repository.ComputadorRepository;
import com.smartlab.monitor.repository.PoliticaHorarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DesligamentoScheduler {

    private static final Logger log = LoggerFactory.getLogger(DesligamentoScheduler.class);
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm");
    private static final List<StatusComputador> ATIVOS =
            List.of(StatusComputador.LIGADO, StatusComputador.INATIVO);

    private final PoliticaHorarioRepository politicaRepository;
    private final ComputadorRepository computadorRepository;

    public DesligamentoScheduler(PoliticaHorarioRepository politicaRepository,
                                 ComputadorRepository computadorRepository) {
        this.politicaRepository = politicaRepository;
        this.computadorRepository = computadorRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void executarDesligamentosFixos() {
        String horaAtual = LocalTime.now().format(FORMATO);
        List<PoliticaHorario> politicasAtivas = politicaRepository.findByHorarioDesligamento(horaAtual);

        for (PoliticaHorario politica : politicasAtivas) {
            Laboratorio lab = politica.getLaboratorio();
            List<Computador> ativos = computadorRepository.findByLaboratorioAndStatusIn(lab, ATIVOS);

            if (ativos.isEmpty()) continue;

            ativos.forEach(pc -> pc.setStatus(StatusComputador.DESLIGADO));
            computadorRepository.saveAll(ativos);

            log.info("[Scheduler] {} — {} computador(es) desligado(s) às {} (política #{})",
                    lab.getNome(), ativos.size(), horaAtual, politica.getId());
        }
    }
}
