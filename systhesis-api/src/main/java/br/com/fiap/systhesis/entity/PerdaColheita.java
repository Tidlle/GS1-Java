package br.com.fiap.systhesis.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PERDA_COLHEITA")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class PerdaColheita extends Evento {

    @Override
    public String getTipoDescricao() {
        return "Perda de Colheita — estufa da colônia perde produção de alimentos.";
    }
}
