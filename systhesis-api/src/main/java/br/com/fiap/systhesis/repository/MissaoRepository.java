package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.enums.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
    List<Missao> findByAtivaTrue();
    List<Missao> findByPlanetaAndAtivaTrue(Planeta planeta);
}
