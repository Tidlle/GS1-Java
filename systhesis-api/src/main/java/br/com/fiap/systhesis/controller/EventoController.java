package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.dto.EventoRequest;
import br.com.fiap.systhesis.dto.EventoResponse;
import br.com.fiap.systhesis.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Eventos que afetam os recursos da colônia")
@SecurityRequirement(name = "bearerAuth")
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Lista todos os eventos ou filtra por colônia quando coloniaId for informado")
    public ResponseEntity<List<EventoResponse>> listar(@RequestParam(required = false) Long coloniaId) {
        if (coloniaId != null) {
            return ResponseEntity.ok(eventoService.listarPorColonia(coloniaId));
        }
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<EntityModel<EventoResponse>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(eventoService.buscarPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Criar evento", description = "Registra um evento e aplica impacto nos recursos da colônia")
    public ResponseEntity<EntityModel<EventoResponse>> criar(@RequestBody @Valid EventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(eventoService.criar(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento", description = "Atualiza título, descrição, colônia e impacto percentual do evento")
    public ResponseEntity<EntityModel<EventoResponse>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoRequest request) {
        return ResponseEntity.ok(toModel(eventoService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir evento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<EventoResponse> toModel(EventoResponse r) {
        return EntityModel.of(r,
                linkTo(methodOn(EventoController.class).buscarPorId(r.id())).withSelfRel(),
                linkTo(methodOn(EventoController.class).listar(null)).withRel("todos-eventos"),
                Link.of("/eventos?coloniaId=" + r.coloniaId()).withRel("eventos-da-colonia"),
                Link.of("/colonias/" + r.coloniaId()).withRel("colonia")
        );
    }
}
