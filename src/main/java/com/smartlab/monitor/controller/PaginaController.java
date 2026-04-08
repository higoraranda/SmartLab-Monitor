package com.smartlab.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/app";
    }

    @GetMapping("/app")
    public String app() {
        return "forward:/index.html";
    }
}
