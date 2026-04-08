package com.smartlab.monitor.controller;

import com.smartlab.monitor.dto.PredioRequest;
import com.smartlab.monitor.dto.PredioResponse;
import com.smartlab.monitor.service.PredioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/predios")
@Tag(name = "Prédios", description = "Gerenciamento de prédios")
public class PredioController {

    private final PredioService predioService;

    public PredioController(PredioService predioService) {
        this.predioService = predioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo prédio")
    public PredioResponse criar(@Valid @RequestBody PredioRequest request) {
        return predioService.criar(request);
    }

    @GetMapping
    @Operation(summary = "Lista todos os prédios")
    public List<PredioResponse> listarTodos() {
        return predioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um prédio pelo id")
    public PredioResponse buscarPorId(@PathVariable Long id) {
        return predioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um prédio existente")
    public PredioResponse atualizar(@PathVariable Long id, @Valid @RequestBody PredioRequest request) {
        return predioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um prédio e todos os seus laboratórios e computadores")
    public void remover(@PathVariable Long id) {
        predioService.remover(id);
    }
}
