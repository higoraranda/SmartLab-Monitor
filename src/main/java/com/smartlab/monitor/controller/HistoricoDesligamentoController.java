package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.HistoricoDesligamentoResponse;
import com.smartlab.monitor.service.HistoricoDesligamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico-desligamento")
@Tag(name = "Histórico de Desligamento", description = "Consulta o histórico de desligamentos por laboratório")
public class HistoricoDesligamentoController {

    private final HistoricoDesligamentoService service;

    public HistoricoDesligamentoController(HistoricoDesligamentoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista o histórico de desligamentos, opcionalmente filtrado por laboratório")
    public List<HistoricoDesligamentoResponse> buscar(
            @RequestParam(required = false) String laboratorio) {
        return service.buscarPorLaboratorio(laboratorio);
    }
}
