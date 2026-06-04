package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.*;

import java.time.LocalDateTime;

public record EventoResponse(
        Long id,
        String titulo,
        String descricao,
        String tipoEvento,
        Double impactoPercentual,
        Long coloniaId,
        String coloniaNome,
        Boolean resolvido,
        LocalDateTime ocorridoEm
) {
    public static EventoResponse from(Evento evento) {
        return new EventoResponse(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                resolverTipo(evento),
                evento.getImpactoPercentual(),
                evento.getColonia().getId(),
                evento.getColonia().getNome(),
                evento.getResolvido(),
                evento.getOcorridoEm()
        );
    }

    private static String resolverTipo(Evento evento) {
        if (evento instanceof TempestadeSolar) return "TEMPESTADE_SOLAR";
        if (evento instanceof FalhaEnergetica)  return "FALHA_ENERGETICA";
        if (evento instanceof VazamentoAgua)    return "VAZAMENTO_AGUA";
        if (evento instanceof PerdaColheita)    return "PERDA_COLHEITA";
        return "DESCONHECIDO";
    }
}
