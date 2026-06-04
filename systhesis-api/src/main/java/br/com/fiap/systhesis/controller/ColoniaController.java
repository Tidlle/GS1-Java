package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.dto.ColoniaRequest;
import br.com.fiap.systhesis.dto.ColoniaResponse;
import br.com.fiap.systhesis.dto.RecursoColoniaResponse;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.service.ColoniaService;
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
@RequestMapping("/colonias")
@RequiredArgsConstructor
@Tag(name = "Colônias", description = "Gerenciamento de colônias espaciais")
@SecurityRequirement(name = "bearerAuth")
public class ColoniaController {

    private final ColoniaService coloniaService;

    @PostMapping
    @Operation(summary = "Criar colônia", description = "Cria uma nova colônia espacial para o usuário autenticado")
    public ResponseEntity<EntityModel<ColoniaResponse>> criar(
            @RequestBody @Valid ColoniaRequest request,
            @AuthenticationPrincipal Usuario usuario) {

        ColoniaResponse response = ColoniaResponse.from(coloniaService.criar(request, usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar colônia por ID")
    public ResponseEntity<EntityModel<ColoniaResponse>> buscarPorId(@PathVariable Long id) {
        ColoniaResponse response = ColoniaResponse.from(coloniaService.buscarPorId(id));
        return ResponseEntity.ok(toModel(response));
    }

    @GetMapping
    @Operation(summary = "Listar minhas colônias")
    public ResponseEntity<List<ColoniaResponse>> listarMinhas(@AuthenticationPrincipal Usuario usuario) {
        List<ColoniaResponse> list = coloniaService.listarPorUsuario(usuario.getId())
                .stream()
                .map(ColoniaResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar colônia")
    public ResponseEntity<EntityModel<ColoniaResponse>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ColoniaRequest request) {

        ColoniaResponse response = ColoniaResponse.from(coloniaService.atualizar(id, request));
        return ResponseEntity.ok(toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir colônia")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        coloniaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recursos")
    @Operation(summary = "Consultar recursos da colônia", description = "Retorna água, energia, oxigênio, alimento e temperatura")
    public ResponseEntity<List<RecursoColoniaResponse>> listarRecursos(@PathVariable Long id) {
        List<RecursoColoniaResponse> recursos = coloniaService.listarRecursos(id)
                .stream()
                .map(RecursoColoniaResponse::from)
                .toList();
        return ResponseEntity.ok(recursos);
    }

    // HATEOAS helper
    private EntityModel<ColoniaResponse> toModel(ColoniaResponse response) {
        return EntityModel.of(response,
                linkTo(methodOn(ColoniaController.class).buscarPorId(response.id())).withSelfRel(),
                linkTo(methodOn(ColoniaController.class).listarRecursos(response.id())).withRel("recursos"),
                Link.of("/missoes?planeta=" + response.localizacao().planeta()).withRel("missoes-disponiveis"),
                Link.of("/eventos?coloniaId=" + response.id()).withRel("eventos")
        );
    }
}
