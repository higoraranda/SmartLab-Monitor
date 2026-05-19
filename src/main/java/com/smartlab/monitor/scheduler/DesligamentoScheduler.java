package com.smartlab.monitor.scheduler;

import com.smartlab.monitor.domain.*;
import com.smartlab.monitor.repository.ComputadorRepository;
import com.smartlab.monitor.repository.PoliticaHorarioRepository;
import com.smartlab.monitor.service.HistoricoDesligamentoService;
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
    private final HistoricoDesligamentoService historicoService;

    public DesligamentoScheduler(PoliticaHorarioRepository politicaRepository,
                                 ComputadorRepository computadorRepository,
                                 HistoricoDesligamentoService historicoService) {
        this.politicaRepository = politicaRepository;
        this.computadorRepository = computadorRepository;
        this.historicoService = historicoService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void executarDesligamentosFixos() {
        String horaAtual = LocalTime.now().format(FORMATO);
        List<PoliticaHorario> politicasAtivas = politicaRepository.findByHorarioDesligamento(horaAtual);

        for (PoliticaHorario politica : politicasAtivas) {
            Laboratorio lab = politica.getLaboratorio();
            List<Computador> ativos = computadorRepository.findByLaboratorioAndStatusIn(lab, ATIVOS);

            if (ativos.isEmpty()) continue;

            for (Computador pc : ativos) {
                pc.setStatus(StatusComputador.DESLIGADO);
                historicoService.registrar(
                        pc.getPatrimonio(),
                        lab.getNome(),
                        lab.getPredio().getNome(),
                        MotivoDesligamento.HORARIO_FIXO
                );
            }
            computadorRepository.saveAll(ativos);

            log.info("[Scheduler] {} — {} computador(es) desligado(s) às {} (política #{})",
                    lab.getNome(), ativos.size(), horaAtual, politica.getId());
        }
    }
}
