package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.dto.MissaoRequest;
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

    public List<Missao> listarAtivas() {
        return missaoRepository.findByAtivaTrue();
    }

    public Missao buscarPorId(Long id) {
        return missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada com id: " + id));
    }

    @Transactional
    public Missao criar(MissaoRequest request, Usuario criador) {
        Missao missao = Missao.builder()
                .titulo(request.titulo())
                .descricao(request.descricao())
                .planeta(request.planeta())
                .dificuldade(request.dificuldade())
                .pontosRecompensa(request.pontosRecompensa() != null ? request.pontosRecompensa() : 100)
                .ativa(true)
                .criador(criador)
                .build();
        return missaoRepository.save(missao);
    }

    @Transactional
    public Missao atualizar(Long id, MissaoRequest request) {
        Missao missao = buscarPorId(id);
        missao.setTitulo(request.titulo());
        missao.setDescricao(request.descricao());
        missao.setPlaneta(request.planeta());
        missao.setDificuldade(request.dificuldade());
        if (request.pontosRecompensa() != null) missao.setPontosRecompensa(request.pontosRecompensa());
        return missaoRepository.save(missao);
    }

    @Transactional
    public void deletar(Long id) {
        Missao missao = buscarPorId(id);
        missao.setAtiva(false); // soft delete
        missaoRepository.save(missao);
    }
}
