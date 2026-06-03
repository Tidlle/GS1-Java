package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.dto.MissaoRequest;
import br.com.fiap.systhesis.service.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/missoes")
@RequiredArgsConstructor
@Tag(name = "Missões", description = "Gerenciamento de missões educacionais")
public class MissaoController {

    private final MissaoService missaoService;

    @GetMapping
    @Operation(summary = "Listar missões ativas")
    public ResponseEntity<List<Missao>> listar() {
        return ResponseEntity.ok(missaoService.listarAtivas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar missão por ID")
    public ResponseEntity<EntityModel<Missao>> buscarPorId(@PathVariable Long id) {
        Missao missao = missaoService.buscarPorId(id);
        return ResponseEntity.ok(EntityModel.of(missao,
                linkTo(methodOn(MissaoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(MissaoController.class).listar()).withRel("todas-missoes")
        ));
    }

    @PostMapping
    @Operation(summary = "Criar missão", description = "Apenas PROFESSOR ou ADMINISTRADOR")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<EntityModel<Missao>> criar(
            @RequestBody @Valid MissaoRequest request,
            @AuthenticationPrincipal Usuario usuario) {

        Missao missao = missaoService.criar(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(missao,
                linkTo(methodOn(MissaoController.class).buscarPorId(missao.getId())).withSelfRel()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar missão")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<EntityModel<Missao>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid MissaoRequest request) {

        Missao missao = missaoService.atualizar(id, request);
        return ResponseEntity.ok(EntityModel.of(missao,
                linkTo(methodOn(MissaoController.class).buscarPorId(id)).withSelfRel()
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar missão (soft delete)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        missaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
