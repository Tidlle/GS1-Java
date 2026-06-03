package br.com.fiap.systhesis.dto;

public record TokenResponse(
        String token,
        String tipo,
        String email,
        String perfil
) {}
