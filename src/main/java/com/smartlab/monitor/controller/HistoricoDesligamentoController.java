package com.smartlab.monitor.controller;

import com.smartlab.monitor.domain.MotivoDesligamento;
import com.smartlab.monitor.dto.HistoricoDesligamentoResponse;
import com.smartlab.monitor.service.HistoricoDesligamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historico-desligamento")
@Tag(name = "Histórico de Desligamento", description = "Consulta o histórico de desligamentos com filtros")
public class HistoricoDesligamentoController {

    private final HistoricoDesligamentoService service;

    public HistoricoDesligamentoController(HistoricoDesligamentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista o histórico de desligamentos com filtros opcionais")
    public List<HistoricoDesligamentoResponse> buscar(
            @RequestParam(required = false) MotivoDesligamento motivo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(required = false) String laboratorio,
            @RequestParam(required = false) String patrimonio) {
        return service.buscarComFiltros(motivo, dataInicio, dataFim, laboratorio, patrimonio);
    }
}
