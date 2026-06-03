package br.com.fiap.systhesis.repository;

import br.com.fiap.systhesis.entity.RecursoColonia;
import br.com.fiap.systhesis.entity.RecursoColoniaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoColoniaRepository extends JpaRepository<RecursoColonia, RecursoColoniaId> {
    List<RecursoColonia> findByColoniaId(Long coloniaId);
}
