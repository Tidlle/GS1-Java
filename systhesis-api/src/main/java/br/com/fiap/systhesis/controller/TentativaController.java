package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.dto.TentativaRequest;
import br.com.fiap.systhesis.entity.Tentativa;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.service.TentativaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/tentativas")
@RequiredArgsConstructor
@Tag(name = "Tentativas", description = "Registro de respostas dos alunos às perguntas")
@SecurityRequirement(name = "bearerAuth")
public class TentativaController {

    private final TentativaService tentativaService;

    @GetMapping
    @Operation(summary = "Listar tentativas", description = "Lista todas as tentativas ou filtra por usuário/colônia")
    public ResponseEntity<List<Tentativa>> listar(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long coloniaId) {

        if (usuarioId != null) {
            return ResponseEntity.ok(tentativaService.listarPorUsuario(usuarioId));
        }

        if (coloniaId != null) {
            return ResponseEntity.ok(tentativaService.listarPorColonia(coloniaId));
        }

        return ResponseEntity.ok(tentativaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tentativa por ID")
    public ResponseEntity<EntityModel<Tentativa>> buscarPorId(@PathVariable Long id) {
        Tentativa tentativa = tentativaService.buscarPorId(id);
        return ResponseEntity.ok(toModel(tentativa));
    }

    @PostMapping
    @Operation(summary = "Registrar resposta", description = "Envia a resposta do aluno e atualiza pontuação da colônia")
    public ResponseEntity<EntityModel<Tentativa>> registrar(
            @RequestBody @Valid TentativaRequest request,
            @AuthenticationPrincipal Usuario usuario) {

        Tentativa tentativa = tentativaService.registrar(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(tentativa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tentativa", description = "Atualiza resposta, pergunta e colônia da tentativa, recalculando a pontuação")
    public ResponseEntity<EntityModel<Tentativa>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TentativaRequest request) {

        Tentativa tentativa = tentativaService.atualizar(id, request);
        return ResponseEntity.ok(toModel(tentativa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tentativa", description = "Exclui a tentativa e remove a pontuação obtida da colônia")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tentativaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Tentativa> toModel(Tentativa tentativa) {
        return EntityModel.of(tentativa,
                linkTo(methodOn(TentativaController.class).buscarPorId(tentativa.getId())).withSelfRel(),
                linkTo(methodOn(TentativaController.class).listar(null, null)).withRel("todas-tentativas"),
                Link.of("/tentativas?usuarioId=" + tentativa.getUsuario().getId()).withRel("tentativas-do-usuario"),
                Link.of("/tentativas?coloniaId=" + tentativa.getColonia().getId()).withRel("tentativas-da-colonia"),
                Link.of("/colonias/" + tentativa.getColonia().getId()).withRel("colonia")
        );
    }
}
