package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findByMissaoId(Long missaoId);
}
