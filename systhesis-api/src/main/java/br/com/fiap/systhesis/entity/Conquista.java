package br.com.fiap.systhesis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_conquista")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, length = 50)
    private String icone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colonia_id")
    private Colonia colonia;

    @Column(name = "obtida_em")
    private LocalDateTime obtidaEm;

    @PrePersist
    public void prePersist() {
        this.obtidaEm = LocalDateTime.now();
    }
}
