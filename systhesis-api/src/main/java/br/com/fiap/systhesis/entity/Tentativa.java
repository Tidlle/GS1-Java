package br.com.fiap.systhesis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tentativa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tentativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colonia_id", nullable = false)
    private Colonia colonia;

    @Column(name = "resposta_enviada", nullable = false, length = 1)
    private String respostaEnviada;

    @Column(name = "correta", nullable = false)
    private Boolean correta;

    @Column(name = "pontos_obtidos")
    private Integer pontosObtidos;

    @Column(name = "respondida_em")
    private LocalDateTime respondidaEm;

    @PrePersist
    public void prePersist() {
        this.respondidaEm = LocalDateTime.now();
    }
}
