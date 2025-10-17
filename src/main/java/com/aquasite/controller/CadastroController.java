package com.aquasite.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cadastros")
public class CadastroController {

    private List<Map<String, Object>> cadastros = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Map<String, Object>> listar() {
        return cadastros;
    }

    @PostMapping
    public Map<String, Object> criar(@RequestBody Map<String, Object> cadastro) {
        cadastro.put("id", nextId++);
        cadastros.add(cadastro);
        return cadastro;
    }

    @GetMapping("/{id}")
    public Map<String, Object> buscar(@PathVariable Long id) {
        return cadastros.stream()
            .filter(c -> c.get("id").equals(id))
            .findFirst()
            .orElse(null);
    }
}