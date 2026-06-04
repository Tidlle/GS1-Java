package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.Tentativa;

import java.time.LocalDateTime;

public record TentativaResponse(
        Long id,
        Long usuarioId,
        String usuarioNome,
        Long perguntaId,
        String perguntaEnunciado,
        Long coloniaId,
        String coloniaNome,
        String respostaEnviada,
        Boolean correta,
        Integer pontosObtidos,
        LocalDateTime respondidaEm
) {
    public static TentativaResponse from(Tentativa tentativa) {
        return new TentativaResponse(
                tentativa.getId(),
                tentativa.getUsuario().getId(),
                tentativa.getUsuario().getNome(),
                tentativa.getPergunta().getId(),
                tentativa.getPergunta().getEnunciado(),
                tentativa.getColonia().getId(),
                tentativa.getColonia().getNome(),
                tentativa.getRespostaEnviada(),
                tentativa.getCorreta(),
                tentativa.getPontosObtidos(),
                tentativa.getRespondidaEm()
        );
    }
}
