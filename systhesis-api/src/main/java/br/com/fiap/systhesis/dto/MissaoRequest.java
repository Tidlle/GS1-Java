package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.enums.Planeta;
import jakarta.validation.constraints.*;

public record MissaoRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
        String titulo,

        String descricao,

        @NotNull(message = "Planeta é obrigatório")
        Planeta planeta,

        @NotNull(message = "Dificuldade é obrigatória")
        @Min(value = 1, message = "Dificuldade mínima é 1")
        @Max(value = 5, message = "Dificuldade máxima é 5")
        Integer dificuldade,

        @Min(value = 0, message = "Pontos de recompensa não podem ser negativos")
        Integer pontosRecompensa
) {}
