package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.enums.Planeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ColoniaRequest(
        @NotBlank(message = "Nome da colônia é obrigatório")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotNull(message = "Planeta é obrigatório")
        Planeta planeta,

        String setor,
        Double latitude,
        Double longitude
) {}
