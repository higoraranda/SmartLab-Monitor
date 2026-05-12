package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.ComputadorRequest;
import com.smartlab.monitor.dto.ComputadorResponse;
import com.smartlab.monitor.service.ComputadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computadores")
@Tag(name = "Computadores", description = "Gerenciamento e monitoramento de computadores")
public class ComputadorController {

    private final ComputadorService computadorService;

    public ComputadorController(ComputadorService computadorService) {
        this.computadorService = computadorService;
    }

    // -------------------------------------------------------------------------
    // CRUD
    // -------------------------------------------------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo computador")
    public ComputadorResponse criar(@Valid @RequestBody ComputadorRequest request) {
        return computadorService.criar(request);
    }

    @GetMapping
    @Operation(summary = "Lista todos os computadores com status de inatividade")
    public List<ComputadorResponse> listarTodos() {
        return computadorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um computador pelo id")
    public ComputadorResponse buscarPorId(@PathVariable Long id) {
        return computadorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados e política de inatividade de um computador")
    public ComputadorResponse atualizar(@PathVariable Long id,
                                        @Valid @RequestBody ComputadorRequest request) {
        return computadorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um computador")
    public void remover(@PathVariable Long id) {
        computadorService.remover(id);
    }

    // -------------------------------------------------------------------------
    // Controle de inatividade
    // -------------------------------------------------------------------------

    @PostMapping("/{id}/atividade")
    @Operation(summary = "Registra atividade de mouse — mantém a máquina como Em Uso")
    public ComputadorResponse registrarAtividade(@PathVariable Long id) {
        return computadorService.registrarAtividade(id);
    }

    @PostMapping("/{id}/ligar")
    @Operation(summary = "Liga o computador e inicia o monitoramento de inatividade")
    public ComputadorResponse ligar(@PathVariable Long id) {
        return computadorService.ligar(id);
    }

    @PostMapping("/{id}/desligar")
    @Operation(summary = "Desliga o computador imediatamente")
    public ComputadorResponse desligar(@PathVariable Long id) {
        return computadorService.desligar(id);
    }
}
