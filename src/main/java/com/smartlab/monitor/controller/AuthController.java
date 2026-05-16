package com.smartlab.monitor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Login do gestor de T.I para gerenciar políticas")
public class AuthController {

    static final String TOKEN = "smartlab-admin-token";

    @PostMapping("/login")
    @Operation(summary = "Autentica o gestor de T.I (usuário: admin, senha: admin)")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String usuario = body.getOrDefault("usuario", "");
        String senha   = body.getOrDefault("senha", "");
        if ("admin".equals(usuario) && "admin".equals(senha)) {
            return Map.of("token", TOKEN);
        }
        throw new IllegalArgumentException("Usuário ou senha inválidos.");
    }
}
