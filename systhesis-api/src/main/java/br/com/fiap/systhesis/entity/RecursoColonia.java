package br.com.fiap.systhesis.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Tabela RecursoColonia com chave composta (coloniaId + tipoRecurso).
 * Requisito: modelagem avançada com @EmbeddedId.
 */
@Entity
@Table(name = "tb_recurso_colonia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecursoColonia {

    @EmbeddedId
    private RecursoColoniaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("coloniaId")
    @JoinColumn(name = "colonia_id")
    private Colonia colonia;

    @Column(nullable = false)
    private Double quantidade;

    @Column(name = "quantidade_maxima", nullable = false)
    private Double quantidadeMaxima;

    public Double getPercentual() {
        return (quantidade / quantidadeMaxima) * 100;
    }

    public boolean isCritico() {
        return getPercentual() <= 20;
    }
}
