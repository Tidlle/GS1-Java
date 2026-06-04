package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.RecursoColonia;
import br.com.fiap.systhesis.enums.TipoRecurso;

public record RecursoColoniaResponse(
        TipoRecurso tipoRecurso,
        Double quantidade,
        Double quantidadeMaxima,
        Double percentual,
        boolean critico
) {
    public static RecursoColoniaResponse from(RecursoColonia recurso) {
        return new RecursoColoniaResponse(
                recurso.getId().getTipoRecurso(),
                recurso.getQuantidade(),
                recurso.getQuantidadeMaxima(),
                recurso.getPercentual(),
                recurso.isCritico()
        );
    }
}
