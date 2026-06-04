package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.enums.Planeta;

public record MissaoResponse(
        Long id,
        String titulo,
        String descricao,
        Planeta planeta,
        Integer dificuldade,
        Integer pontosRecompensa,
        Boolean ativa
) {
    public static MissaoResponse from(Missao missao) {
        return new MissaoResponse(
                missao.getId(),
                missao.getTitulo(),
                missao.getDescricao(),
                missao.getPlaneta(),
                missao.getDificuldade(),
                missao.getPontosRecompensa(),
                missao.getAtiva()
        );
    }
}
