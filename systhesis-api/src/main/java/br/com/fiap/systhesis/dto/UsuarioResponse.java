package br.com.fiap.systhesis.dto;

import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.enums.PerfilUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        PerfilUsuario perfil,
        LocalDateTime criadoEm
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getCriadoEm()
        );
    }
}
