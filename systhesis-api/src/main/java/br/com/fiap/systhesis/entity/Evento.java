package br.com.fiap.systhesis.entity;

import br.com.fiap.systhesis.entity.Colonia;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Classe base de Evento com herança usando SINGLE_TABLE.
 * Requisito: modelagem avançada com herança JPA.
 */
@Entity
@Table(name = "tb_evento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_evento", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colonia_id", nullable = false)
    private Colonia colonia;

    @Column(name = "impacto_percentual", nullable = false)
    private Double impactoPercentual; // % de redução ou aumento do recurso

    @Column(name = "resolvido")
    private Boolean resolvido;

    @Column(name = "ocorrido_em")
    private LocalDateTime ocorridoEm;

    @PrePersist
    public void prePersist() {
        this.ocorridoEm = LocalDateTime.now();
        if (this.resolvido == null) this.resolvido = false;
    }

    public abstract String getTipoDescricao();
}
