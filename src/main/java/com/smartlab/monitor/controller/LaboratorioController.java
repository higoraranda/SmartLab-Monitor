package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.LaboratorioRequest;
import com.smartlab.monitor.dto.LaboratorioResponse;
import com.smartlab.monitor.service.LaboratorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorios")
@Tag(name = "Laboratórios", description = "Gerenciamento de laboratórios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo laboratório")
    public LaboratorioResponse criar(@Valid @RequestBody LaboratorioRequest request) {
        return laboratorioService.criar(request);
    }

    @GetMapping
    @Operation(summary = "Lista todos os laboratórios")
    public List<LaboratorioResponse> listarTodos() {
        return laboratorioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um laboratório pelo id")
    public LaboratorioResponse buscarPorId(@PathVariable Long id) {
        return laboratorioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um laboratório existente")
    public LaboratorioResponse atualizar(@PathVariable Long id, @Valid @RequestBody LaboratorioRequest request) {
        return laboratorioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um laboratório e todos os seus computadores")
    public void remover(@PathVariable Long id) {
        laboratorioService.remover(id);
    }
}
