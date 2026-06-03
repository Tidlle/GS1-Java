package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.dto.EventoRequest;
import br.com.fiap.systhesis.entity.*;
import br.com.fiap.systhesis.enums.TipoRecurso;
import br.com.fiap.systhesis.exception.RecursoNaoEncontradoException;
import br.com.fiap.systhesis.repository.ColoniaRepository;
import br.com.fiap.systhesis.repository.EventoRepository;
import br.com.fiap.systhesis.repository.RecursoColoniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ColoniaRepository coloniaRepository;
    private final RecursoColoniaRepository recursoColoniaRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public List<Evento> listarPorColonia(Long coloniaId) {
        coloniaRepository.findById(coloniaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada: " + coloniaId));
        return eventoRepository.findByColoniaId(coloniaId);
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com id: " + id));
    }

    @Transactional
    public Evento criar(EventoRequest request) {
        Colonia colonia = buscarColonia(request.coloniaId());
        Evento evento = construirEvento(request, colonia);

        evento = eventoRepository.save(evento);
        aplicarImpactoNosRecursos(request, colonia);

        return evento;
    }

    @Transactional
    public Evento atualizar(Long id, EventoRequest request) {
        Evento evento = buscarPorId(id);

        if (!getTipoEvento(evento).equals(request.tipoEvento())) {
            throw new IllegalArgumentException(
                    "Não é permitido alterar o tipo do evento. Exclua o evento atual e crie um novo com o tipo desejado."
            );
        }

        Colonia colonia = buscarColonia(request.coloniaId());

        evento.setTitulo(request.titulo());
        evento.setDescricao(request.descricao());
        evento.setColonia(colonia);
        evento.setImpactoPercentual(request.impactoPercentual());

        return eventoRepository.save(evento);
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = buscarPorId(id);
        eventoRepository.delete(evento);
    }

    private Colonia buscarColonia(Long coloniaId) {
        return coloniaRepository.findById(coloniaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada: " + coloniaId));
    }

    private Evento construirEvento(EventoRequest request, Colonia colonia) {
        return switch (request.tipoEvento()) {
            case "TEMPESTADE_SOLAR" -> TempestadeSolar.builder()
                    .titulo(request.titulo())
                    .descricao(request.descricao())
                    .colonia(colonia)
                    .impactoPercentual(request.impactoPercentual())
                    .build();
            case "FALHA_ENERGETICA" -> FalhaEnergetica.builder()
                    .titulo(request.titulo())
                    .descricao(request.descricao())
                    .colonia(colonia)
                    .impactoPercentual(request.impactoPercentual())
                    .build();
            case "VAZAMENTO_AGUA" -> VazamentoAgua.builder()
                    .titulo(request.titulo())
                    .descricao(request.descricao())
                    .colonia(colonia)
                    .impactoPercentual(request.impactoPercentual())
                    .build();
            case "PERDA_COLHEITA" -> PerdaColheita.builder()
                    .titulo(request.titulo())
                    .descricao(request.descricao())
                    .colonia(colonia)
                    .impactoPercentual(request.impactoPercentual())
                    .build();
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + request.tipoEvento());
        };
    }

    private void aplicarImpactoNosRecursos(EventoRequest request, Colonia colonia) {
        TipoRecurso tipoImpactado = switch (request.tipoEvento()) {
            case "TEMPESTADE_SOLAR", "FALHA_ENERGETICA" -> TipoRecurso.ENERGIA;
            case "VAZAMENTO_AGUA" -> TipoRecurso.AGUA;
            case "PERDA_COLHEITA" -> TipoRecurso.ALIMENTO;
            default -> null;
        };

        if (tipoImpactado != null) {
            RecursoColoniaId recursoId = new RecursoColoniaId(colonia.getId(), tipoImpactado);
            recursoColoniaRepository.findById(recursoId).ifPresent(recurso -> {
                double reducao = recurso.getQuantidade() * (request.impactoPercentual() / 100);
                recurso.setQuantidade(Math.max(0, recurso.getQuantidade() - reducao));
                recursoColoniaRepository.save(recurso);
            });
        }
    }

    private String getTipoEvento(Evento evento) {
        if (evento instanceof TempestadeSolar) return "TEMPESTADE_SOLAR";
        if (evento instanceof FalhaEnergetica) return "FALHA_ENERGETICA";
        if (evento instanceof VazamentoAgua) return "VAZAMENTO_AGUA";
        if (evento instanceof PerdaColheita) return "PERDA_COLHEITA";
        throw new IllegalArgumentException("Tipo de evento não reconhecido.");
    }
}
