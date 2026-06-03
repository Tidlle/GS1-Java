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

    /** Embedded — localização espacial da base */
    @Embedded
    private LocalizacaoEspacial localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusColonia status;

    @Column(name = "pontuacao_total")
    private Integer pontuacaoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "colonia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RecursoColonia> recursos = new ArrayList<>();

    @Column(name = "criada_em")
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        this.criadaEm = LocalDateTime.now();
        if (this.status == null) this.status = StatusColonia.ATIVA;
        if (this.pontuacaoTotal == null) this.pontuacaoTotal = 0;
    }
}
