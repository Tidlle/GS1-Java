package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.Tentativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TentativaRepository extends JpaRepository<Tentativa, Long> {
    List<Tentativa> findByUsuarioId(Long usuarioId);
    List<Tentativa> findByColoniaId(Long coloniaId);

    @Query("SELECT SUM(t.pontosObtidos) FROM Tentativa t WHERE t.usuario.id = :usuarioId")
    Integer somarPontosPorUsuario(Long usuarioId);
}
