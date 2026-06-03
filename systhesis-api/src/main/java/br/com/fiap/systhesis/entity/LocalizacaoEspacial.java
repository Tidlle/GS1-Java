package br.com.fiap.systhesis.entity;

import br.com.fiap.systhesis.enums.Planeta;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

/**
 * Classe Embedded — representa a localização espacial da colônia.
 * Requisito: uso de @Embeddable para modelagem avançada.
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LocalizacaoEspacial {

    @Enumerated(EnumType.STRING)
    private Planeta planeta;

    private String setor;

    private Double latitude;

    private Double longitude;
}
