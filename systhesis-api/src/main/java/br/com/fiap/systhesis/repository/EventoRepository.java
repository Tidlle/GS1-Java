package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByColoniaId(Long coloniaId);
    List<Evento> findByColoniaIdAndResolvidoFalse(Long coloniaId);
}
