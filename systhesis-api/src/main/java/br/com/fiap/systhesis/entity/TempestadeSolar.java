package br.com.fiap.systhesis.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("TEMPESTADE_SOLAR")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class TempestadeSolar extends Evento {

    @Override
    public String getTipoDescricao() {
        return "Tempestade Solar — reduz energia disponível na base.";
    }
}
