package br.com.fiap.systhesis.entity;

import br.com.fiap.systhesis.enums.StatusColonia;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_colonia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    /** Embedded — localização espacial da base (modelagem avançada: @Embeddable) */
    @Embedded
    private LocalizacaoEspacial localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusColonia status;

    // ── Recursos diretos (integração mobile) ────────────────────────────────────

    @Builder.Default
    @Column(nullable = false)
    private Integer agua = 70;

    @Builder.Default
    @Column(nullable = false)
    private Integer energia = 80;

    @Builder.Default
    @Column(nullable = false)
    private Integer oxigenio = 90;

    @Builder.Default
    @Column(nullable = false)
    private Integer alimento = 40;

    @Builder.Default
    @Column(nullable = false)
    private Integer temperatura = 22;

    // ── Progressão do jogador ────────────────────────────────────────────────────

    @Builder.Default
    @Column(nullable = false)
    private Integer nivel = 1;

    @Builder.Default
    @Column(nullable = false)
    private Integer xp = 0;

    @Builder.Default
    @Column(name = "pontuacao_total", nullable = false)
    private Integer pontuacaoTotal = 0;

    // ── Relacionamentos ──────────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /** Modelagem avançada: @OneToMany com chave composta @EmbeddedId */
    @OneToMany(mappedBy = "colonia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RecursoColonia> recursos = new ArrayList<>();

    @Column(name = "criada_em")
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        this.criadaEm = LocalDateTime.now();
        if (this.status       == null) this.status       = StatusColonia.ATIVA;
        if (this.agua         == null) this.agua         = 70;
        if (this.energia      == null) this.energia      = 80;
        if (this.oxigenio     == null) this.oxigenio     = 90;
        if (this.alimento     == null) this.alimento     = 40;
        if (this.temperatura  == null) this.temperatura  = 22;
        if (this.nivel        == null) this.nivel        = 1;
        if (this.xp           == null) this.xp           = 0;
        if (this.pontuacaoTotal == null) this.pontuacaoTotal = 0;
    }
}
