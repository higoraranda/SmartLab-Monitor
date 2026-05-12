package com.smartlab.monitor.scheduler;

import com.smartlab.monitor.service.ComputadorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InatividadeScheduler {

    private final ComputadorService computadorService;

    public InatividadeScheduler(ComputadorService computadorService) {
        this.computadorService = computadorService;
    }

    /**
     * Roda a cada 30 segundos e verifica todos os computadores ativos.
     * Se a última atividade de mouse ultrapassou o tempo configurado →
     *   EM_USO → OCIOSO (aviso de 1 minuto antes de desligar)
     *   OCIOSO → DESLIGADO (após 1 minuto extra)
     */
    @Scheduled(fixedRate = 30_000)
    public void verificarInatividade() {
        computadorService.verificarInatividade();
    }
}
