package br.com.fiap.systhesis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TentativaRequest(
        @NotNull(message = "ID da pergunta é obrigatório")
        Long perguntaId,

        @NotNull(message = "ID da colônia é obrigatório")
        Long coloniaId,

        @NotBlank(message = "Resposta enviada é obrigatória")
        @Pattern(regexp = "[ABCDabcd]", message = "Resposta deve ser A, B, C ou D")
        String respostaEnviada
) {}
