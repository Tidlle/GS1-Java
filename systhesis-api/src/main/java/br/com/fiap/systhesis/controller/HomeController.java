package br.com.fiap.systhesis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, String>> index() {
        return ResponseEntity.ok(Map.of(
                "status", "online",
                "app", "Systhesis API",
                "descricao", "Simulador Gamificado de Colonização Espacial",
                "versao", "1.0.0",
                "docs", "/swagger-ui.html"
        ));
    }
}
