package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.dto.CadastroUsuarioRequest;
import br.com.fiap.systhesis.dto.LoginRequest;
import br.com.fiap.systhesis.dto.TokenResponse;
import br.com.fiap.systhesis.exception.CredenciaisInvalidasException;
import br.com.fiap.systhesis.exception.EmailJaCadastradoException;
import br.com.fiap.systhesis.repository.UsuarioRepository;
import br.com.fiap.systhesis.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );
        } catch (AuthenticationException e) {
            log.warn("Falha de autenticação para [{}]: {}", request.email(), e.getMessage());
            throw new CredenciaisInvalidasException();
        }

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(CredenciaisInvalidasException::new);

        try {
            String token = jwtService.gerarToken(usuario);
            return new TokenResponse(token, "Bearer", usuario.getEmail(), usuario.getPerfil().name());
        } catch (Exception e) {
            log.error("Erro ao gerar token JWT para [{}]: {}", request.email(), e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar token de autenticação.", e);
        }
    }

    public Usuario cadastrar(CadastroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException(request.email());
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .perfil(request.perfil())
                .build();

        return usuarioRepository.save(usuario);
    }
}
