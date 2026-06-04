package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.LocalizacaoEspacial;
import br.com.fiap.systhesis.enums.Planeta;

public record LocalizacaoResponse(
        Planeta planeta,
        String setor,
        Double latitude,
        Double longitude
) {
    public static LocalizacaoResponse from(LocalizacaoEspacial loc) {
        if (loc == null) return null;
        return new LocalizacaoResponse(
                loc.getPlaneta(),
                loc.getSetor(),
                loc.getLatitude(),
                loc.getLongitude()
        );
    }
}
