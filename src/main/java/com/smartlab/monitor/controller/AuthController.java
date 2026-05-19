package com.smartlab.monitor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Login do gestor para gerenciar políticas")
public class AuthController {

    @GetMapping("/me")
    @Operation(summary = "Retorna se o usuário atual está autenticado como gestor")
    public Map<String, Object> me(Authentication auth) {
        boolean logado = auth != null && auth.isAuthenticated();
        if (logado) {
            return Map.of("autenticado", true, "usuario", auth.getName());
        }
        return Map.of("autenticado", false);
    }
}
