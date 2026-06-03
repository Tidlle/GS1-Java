package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.dto.EventoRequest;
import br.com.fiap.systhesis.entity.Evento;
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
    public ResponseEntity<List<Evento>> listar(@RequestParam(required = false) Long coloniaId) {
        if (coloniaId != null) {
            return ResponseEntity.ok(eventoService.listarPorColonia(coloniaId));
        }
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<EntityModel<Evento>> buscarPorId(@PathVariable Long id) {
        Evento evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(toModel(evento));
    }

    @PostMapping
    @Operation(summary = "Criar evento", description = "Registra um evento e aplica impacto nos recursos da colônia")
    public ResponseEntity<EntityModel<Evento>> criar(@RequestBody @Valid EventoRequest request) {
        Evento evento = eventoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(evento));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento", description = "Atualiza título, descrição, colônia e impacto percentual do evento")
    public ResponseEntity<EntityModel<Evento>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoRequest request) {

        Evento evento = eventoService.atualizar(id, request);
        return ResponseEntity.ok(toModel(evento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir evento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Evento> toModel(Evento evento) {
        return EntityModel.of(evento,
                linkTo(methodOn(EventoController.class).buscarPorId(evento.getId())).withSelfRel(),
                linkTo(methodOn(EventoController.class).listar(null)).withRel("todos-eventos"),
                Link.of("/eventos?coloniaId=" + evento.getColonia().getId()).withRel("eventos-da-colonia"),
                Link.of("/colonias/" + evento.getColonia().getId()).withRel("colonia")
        );
    }
}
