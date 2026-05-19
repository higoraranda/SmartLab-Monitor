package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.InatividadeRequest;
import com.smartlab.monitor.dto.LaboratorioResponse;
import com.smartlab.monitor.service.PoliticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/politicas")
@Tag(name = "Políticas", description = "Gerenciamento de tempo de inatividade por laboratório")
public class PoliticaController {

    private final PoliticaService politicaService;

    public PoliticaController(PoliticaService politicaService) {
        this.politicaService = politicaService;
    }

    @PutMapping("/{labId}/inatividade")
    @Operation(summary = "Define o tempo limite de inatividade do laboratório (em segundos)")
    public LaboratorioResponse definirInatividade(
            @PathVariable Long labId,
            @Valid @RequestBody InatividadeRequest request) {
        return politicaService.definirInatividade(labId, request.tempoOciosoGlobal());
    }
}
