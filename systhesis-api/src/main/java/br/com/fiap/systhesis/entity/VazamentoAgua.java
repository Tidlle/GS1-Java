package br.com.fiap.systhesis.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("VAZAMENTO_AGUA")
@Getter @Setter @NoArgsConstructor @SuperBuilder
public class VazamentoAgua extends Evento {

    @Override
    public String getTipoDescricao() {
        return "Vazamento de Água — reservatório da base perde volume crítico.";
    }
}
