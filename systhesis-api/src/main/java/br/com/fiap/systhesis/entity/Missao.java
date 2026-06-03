package br.com.fiap.systhesis.entity;

import br.com.fiap.systhesis.enums.Planeta;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_missao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Planeta planeta;

    @Column(nullable = false)
    private Integer dificuldade; // 1 a 5

    @Column(name = "pontos_recompensa")
    private Integer pontosRecompensa;

    @Column(nullable = false)
    private Boolean ativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Pergunta> perguntas = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.ativa == null) this.ativa = true;
        if (this.pontosRecompensa == null) this.pontosRecompensa = 100;
    }
}
