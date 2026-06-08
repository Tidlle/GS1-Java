package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.entity.LocalizacaoEspacial;
import br.com.fiap.systhesis.enums.Planeta;
import br.com.fiap.systhesis.enums.StatusColonia;

import java.time.LocalDateTime;

public record ColoniaResponse(
        Long id,
        String nome,
        Planeta planeta,
        String setor,
        Double latitude,
        Double longitude,
        StatusColonia status,
        Integer agua,
        Integer energia,
        Integer oxigenio,
        Integer alimento,
        Integer temperatura,
        Integer nivel,
        Integer xp,
        Integer pontuacaoTotal,
        LocalDateTime criadaEm
) {
    public static ColoniaResponse from(Colonia c) {
        LocalizacaoEspacial loc = c.getLocalizacao();
        return new ColoniaResponse(
                c.getId(),
                c.getNome(),
                loc != null ? loc.getPlaneta()  : null,
                loc != null ? loc.getSetor()    : null,
                loc != null ? loc.getLatitude() : null,
                loc != null ? loc.getLongitude(): null,
                c.getStatus(),
                c.getAgua(),
                c.getEnergia(),
                c.getOxigenio(),
                c.getAlimento(),
                c.getTemperatura(),
                c.getNivel(),
                c.getXp(),
                c.getPontuacaoTotal(),
                c.getCriadaEm()
        );
    }
}
