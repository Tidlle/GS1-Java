package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.service.ColoniaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
@Tag(name = "Ranking", description = "Classificação dos jogadores por pontuação")
public class RankingController {

    private final ColoniaService coloniaService;

    @GetMapping
    @Operation(summary = "Consultar ranking", description = "Lista colônias ordenadas por pontuação total (público)")
    public ResponseEntity<List<Colonia>> ranking() {
        return ResponseEntity.ok(coloniaService.ranking());
    }
}
