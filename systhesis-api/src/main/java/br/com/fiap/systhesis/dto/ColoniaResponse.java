package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.enums.StatusColonia;

import java.time.LocalDateTime;

public record ColoniaResponse(
        Long id,
        String nome,
        LocalizacaoResponse localizacao,
        StatusColonia status,
        Integer pontuacaoTotal,
        Long usuarioId,
        LocalDateTime criadaEm
) {
    public static ColoniaResponse from(Colonia colonia) {
        return new ColoniaResponse(
                colonia.getId(),
                colonia.getNome(),
                LocalizacaoResponse.from(colonia.getLocalizacao()),
                colonia.getStatus(),
                colonia.getPontuacaoTotal(),
                colonia.getUsuario().getId(),
                colonia.getCriadaEm()
        );
    }
}
