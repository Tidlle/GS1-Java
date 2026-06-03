package br.com.fiap.systhesis.controller;

import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.dto.CadastroUsuarioRequest;
import br.com.fiap.systhesis.dto.LoginRequest;
import br.com.fiap.systhesis.dto.TokenResponse;
import br.com.fiap.systhesis.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de login e cadastro")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna o token JWT")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário (aluno, professor ou administrador)")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid CadastroUsuarioRequest request) {
        Usuario usuario = authService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}
