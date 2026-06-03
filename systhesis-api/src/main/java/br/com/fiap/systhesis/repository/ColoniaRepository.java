package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
    List<Colonia> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Colonia c ORDER BY c.pontuacaoTotal DESC")
    List<Colonia> findRanking();
}
