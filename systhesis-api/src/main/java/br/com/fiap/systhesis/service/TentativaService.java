package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.dto.TentativaRequest;
import br.com.fiap.systhesis.dto.TentativaResponse;
import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.entity.Pergunta;
import br.com.fiap.systhesis.entity.Tentativa;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.exception.RecursoNaoEncontradoException;
import br.com.fiap.systhesis.repository.ColoniaRepository;
import br.com.fiap.systhesis.repository.PerguntaRepository;
import br.com.fiap.systhesis.repository.TentativaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TentativaService {

    private final TentativaRepository tentativaRepository;
    private final PerguntaRepository perguntaRepository;
    private final ColoniaRepository coloniaRepository;

    @Transactional(readOnly = true)
    public List<TentativaResponse> listarTodas() {
        return tentativaRepository.findAll()
                .stream()
                .map(TentativaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TentativaResponse> listarPorUsuario(Long usuarioId) {
        return tentativaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(TentativaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TentativaResponse> listarPorColonia(Long coloniaId) {
        coloniaRepository.findById(coloniaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada com id: " + coloniaId));
        return tentativaRepository.findByColoniaId(coloniaId)
                .stream()
                .map(TentativaResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TentativaResponse buscarPorId(Long id) {
        return TentativaResponse.from(findTentativa(id));
    }

    @Transactional
    public TentativaResponse registrar(TentativaRequest request, Usuario usuario) {
        Pergunta pergunta = buscarPergunta(request.perguntaId());
        Colonia colonia   = buscarColonia(request.coloniaId());

        boolean correta = respostaEstaCorreta(pergunta, request.respostaEnviada());
        int pontos      = calcularPontos(pergunta, correta);

        if (correta) {
            adicionarPontos(colonia, pontos);
        }

        Tentativa tentativa = Tentativa.builder()
                .usuario(usuario)
                .pergunta(pergunta)
                .colonia(colonia)
                .respostaEnviada(request.respostaEnviada().toUpperCase())
                .correta(correta)
                .pontosObtidos(pontos)
                .build();

        return TentativaResponse.from(tentativaRepository.save(tentativa));
    }

    @Transactional
    public TentativaResponse atualizar(Long id, TentativaRequest request) {
        Tentativa tentativa = findTentativa(id);
        removerPontuacaoAnterior(tentativa);

        Pergunta pergunta = buscarPergunta(request.perguntaId());
        Colonia colonia   = buscarColonia(request.coloniaId());
        boolean correta   = respostaEstaCorreta(pergunta, request.respostaEnviada());
        int pontos        = calcularPontos(pergunta, correta);

        if (correta) {
            adicionarPontos(colonia, pontos);
        }

        tentativa.setPergunta(pergunta);
        tentativa.setColonia(colonia);
        tentativa.setRespostaEnviada(request.respostaEnviada().toUpperCase());
        tentativa.setCorreta(correta);
        tentativa.setPontosObtidos(pontos);

        return TentativaResponse.from(tentativaRepository.save(tentativa));
    }

    @Transactional
    public void deletar(Long id) {
        Tentativa tentativa = findTentativa(id);
        removerPontuacaoAnterior(tentativa);
        tentativaRepository.delete(tentativa);
    }

    // ── métodos internos ────────────────────────────────────────────────────────

    private Tentativa findTentativa(Long id) {
        return tentativaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tentativa não encontrada com id: " + id));
    }

    private Pergunta buscarPergunta(Long perguntaId) {
        return perguntaRepository.findById(perguntaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pergunta não encontrada com id: " + perguntaId));
    }

    private Colonia buscarColonia(Long coloniaId) {
        return coloniaRepository.findById(coloniaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada com id: " + coloniaId));
    }

    private boolean respostaEstaCorreta(Pergunta pergunta, String respostaEnviada) {
        return pergunta.getRespostaCorreta().equalsIgnoreCase(respostaEnviada);
    }

    private int calcularPontos(Pergunta pergunta, boolean correta) {
        return correta ? pergunta.getMissao().getPontosRecompensa() : 0;
    }

    private void adicionarPontos(Colonia colonia, int pontos) {
        colonia.setPontuacaoTotal(colonia.getPontuacaoTotal() + pontos);
        coloniaRepository.save(colonia);
    }

    private void removerPontuacaoAnterior(Tentativa tentativa) {
        Integer pontosObtidos = tentativa.getPontosObtidos();
        if (Boolean.TRUE.equals(tentativa.getCorreta()) && pontosObtidos != null && pontosObtidos > 0) {
            Colonia colonia = tentativa.getColonia();
            colonia.setPontuacaoTotal(Math.max(0, colonia.getPontuacaoTotal() - pontosObtidos));
            coloniaRepository.save(colonia);
        }
    }
}
