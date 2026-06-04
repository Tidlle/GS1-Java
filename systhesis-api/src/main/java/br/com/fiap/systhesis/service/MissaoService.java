package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.dto.MissaoRequest;
import br.com.fiap.systhesis.dto.MissaoResponse;
import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.exception.RecursoNaoEncontradoException;
import br.com.fiap.systhesis.repository.MissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissaoService {

    private final MissaoRepository missaoRepository;

    @Transactional(readOnly = true)
    public List<MissaoResponse> listarAtivas() {
        return missaoRepository.findByAtivaTrue()
                .stream()
                .map(MissaoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MissaoResponse buscarPorId(Long id) {
        return MissaoResponse.from(findMissao(id));
    }

    @Transactional
    public MissaoResponse criar(MissaoRequest request, Usuario criador) {
        Missao missao = Missao.builder()
                .titulo(request.titulo())
                .descricao(request.descricao())
                .planeta(request.planeta())
                .dificuldade(request.dificuldade())
                .pontosRecompensa(request.pontosRecompensa() != null ? request.pontosRecompensa() : 100)
                .ativa(true)
                .criador(criador)
                .build();
        return MissaoResponse.from(missaoRepository.save(missao));
    }

    @Transactional
    public MissaoResponse atualizar(Long id, MissaoRequest request) {
        Missao missao = findMissao(id);
        missao.setTitulo(request.titulo());
        missao.setDescricao(request.descricao());
        missao.setPlaneta(request.planeta());
        missao.setDificuldade(request.dificuldade());
        if (request.pontosRecompensa() != null) missao.setPontosRecompensa(request.pontosRecompensa());
        return MissaoResponse.from(missaoRepository.save(missao));
    }

    @Transactional
    public void deletar(Long id) {
        Missao missao = findMissao(id);
        missao.setAtiva(false); // soft delete
        missaoRepository.save(missao);
    }

    // ── método interno ──────────────────────────────────────────────────────────

    private Missao findMissao(Long id) {
        return missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada com id: " + id));
    }
}
