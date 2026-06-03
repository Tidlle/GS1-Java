package br.com.fiap.systhesis.dto;

import jakarta.validation.constraints.*;

public record EventoRequest(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        String descricao,

        @NotNull(message = "ID da colônia é obrigatório")
        Long coloniaId,

        @NotBlank(message = "Tipo do evento é obrigatório")
        @Pattern(regexp = "TEMPESTADE_SOLAR|FALHA_ENERGETICA|VAZAMENTO_AGUA|PERDA_COLHEITA",
                message = "Tipo inválido. Use: TEMPESTADE_SOLAR, FALHA_ENERGETICA, VAZAMENTO_AGUA ou PERDA_COLHEITA")
        String tipoEvento,

        @NotNull(message = "Impacto percentual é obrigatório")
        @DecimalMin(value = "0.1", message = "Impacto deve ser maior que 0")
        @DecimalMax(value = "100.0", message = "Impacto não pode exceder 100%")
        Double impactoPercentual
) {}
