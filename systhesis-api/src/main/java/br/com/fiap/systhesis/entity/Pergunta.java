package br.com.fiap.systhesis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_pergunta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @Column(name = "alternativa_a", nullable = false)
    private String alternativaA;

    @Column(name = "alternativa_b", nullable = false)
    private String alternativaB;

    @Column(name = "alternativa_c")
    private String alternativaC;

    @Column(name = "alternativa_d")
    private String alternativaD;

    @Column(name = "resposta_correta", nullable = false, length = 1)
    private String respostaCorreta; // A, B, C ou D

    @Column(columnDefinition = "TEXT")
    private String explicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;
}
