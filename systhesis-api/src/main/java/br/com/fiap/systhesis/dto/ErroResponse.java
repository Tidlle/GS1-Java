package br.com.fiap.systhesis.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        int status,
        String erro,
        String mensagem,
        LocalDateTime timestamp,
        List<String> detalhes
) {
    public ErroResponse(int status, String erro, String mensagem) {
        this(status, erro, mensagem, LocalDateTime.now(), null);
    }

    public ErroResponse(int status, String erro, String mensagem, List<String> detalhes) {
        this(status, erro, mensagem, LocalDateTime.now(), detalhes);
    }
}
