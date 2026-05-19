package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.PoliticaHorarioRequest;
import com.smartlab.monitor.dto.PoliticaHorarioResponse;
import com.smartlab.monitor.service.PoliticaHorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/politicas-horario")
@Tag(name = "Políticas de Horário", description = "CRUD de políticas de ligamento e desligamento por laboratório")
public class PoliticaHorarioController {

    private final PoliticaHorarioService service;

    public PoliticaHorarioController(PoliticaHorarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova política de horário")
    public PoliticaHorarioResponse criar(@Valid @RequestBody PoliticaHorarioRequest request) {
        return service.criar(request);
    }

    @GetMapping
    @Operation(summary = "Lista todas as políticas de horário")
    public List<PoliticaHorarioResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/laboratorio/{laboratorioId}")
    @Operation(summary = "Lista as políticas de um laboratório específico")
    public List<PoliticaHorarioResponse> listarPorLaboratorio(@PathVariable Long laboratorioId) {
        return service.listarPorLaboratorio(laboratorioId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma política pelo ID")
    public PoliticaHorarioResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma política de horário")
    public PoliticaHorarioResponse atualizar(@PathVariable Long id,
                                              @Valid @RequestBody PoliticaHorarioRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove uma política de horário")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
