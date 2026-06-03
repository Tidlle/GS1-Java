package br.com.fiap.systhesis.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FALHA_ENERGETICA")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class FalhaEnergetica extends Evento {

    @Override
    public String getTipoDescricao() {
        return "Falha Energética — sistema de energia da base sofre colapso parcial.";
    }
}
