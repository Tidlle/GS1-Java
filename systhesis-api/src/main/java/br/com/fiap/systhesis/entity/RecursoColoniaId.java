package br.com.fiap.systhesis.entity;

import br.com.fiap.systhesis.enums.TipoRecurso;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

/**
 * Chave composta para RecursoColonia.
 * Requisito: uso de @EmbeddedId para chave composta.
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class RecursoColoniaId implements Serializable {

    private Long coloniaId;

    @Enumerated(EnumType.STRING)
    private TipoRecurso tipoRecurso;
}
