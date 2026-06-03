package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.entity.RecursoColonia;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.dto.ColoniaRequest;
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
    public ResponseEntity<EntityModel<Colonia>> criar(
            @RequestBody @Valid ColoniaRequest request,
            @AuthenticationPrincipal Usuario usuario) {

        Colonia colonia = coloniaService.criar(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(colonia));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar colônia por ID")
    public ResponseEntity<EntityModel<Colonia>> buscarPorId(@PathVariable Long id) {
        Colonia colonia = coloniaService.buscarPorId(id);
        return ResponseEntity.ok(toModel(colonia));
    }

    @GetMapping
    @Operation(summary = "Listar minhas colônias")
    public ResponseEntity<List<Colonia>> listarMinhas(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(coloniaService.listarPorUsuario(usuario.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar colônia")
    public ResponseEntity<EntityModel<Colonia>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ColoniaRequest request) {

        Colonia colonia = coloniaService.atualizar(id, request);
        return ResponseEntity.ok(toModel(colonia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir colônia")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        coloniaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recursos")
    @Operation(summary = "Consultar recursos da colônia", description = "Retorna água, energia, oxigênio, alimento e temperatura")
    public ResponseEntity<List<RecursoColonia>> listarRecursos(@PathVariable Long id) {
        return ResponseEntity.ok(coloniaService.listarRecursos(id));
    }

    // HATEOAS helper
    private EntityModel<Colonia> toModel(Colonia colonia) {
        return EntityModel.of(colonia,
                linkTo(methodOn(ColoniaController.class).buscarPorId(colonia.getId())).withSelfRel(),
                linkTo(methodOn(ColoniaController.class).listarRecursos(colonia.getId())).withRel("recursos"),
                Link.of("/missoes?planeta=" + colonia.getLocalizacao().getPlaneta()).withRel("missoes-disponiveis"),
                Link.of("/eventos?coloniaId=" + colonia.getId()).withRel("eventos")
        );
    }
}
