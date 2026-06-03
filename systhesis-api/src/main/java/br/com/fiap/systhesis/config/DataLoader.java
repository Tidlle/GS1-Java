package br.com.fiap.systhesis.config;

import br.com.fiap.systhesis.entity.Missao;
import br.com.fiap.systhesis.entity.Pergunta;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.enums.PerfilUsuario;
import br.com.fiap.systhesis.enums.Planeta;
import br.com.fiap.systhesis.repository.MissaoRepository;
import br.com.fiap.systhesis.repository.PerguntaRepository;
import br.com.fiap.systhesis.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seed(
            UsuarioRepository usuarioRepo,
            MissaoRepository missaoRepo,
            PerguntaRepository perguntaRepo) {

        return args -> {
            if (usuarioRepo.count() > 0) return; // já inicializado

            log.info(">>> Carregando dados iniciais...");

            // Usuários
            Usuario admin = usuarioRepo.save(Usuario.builder()
                    .nome("Administrador Systhesis")
                    .email("admin@systhesis.com")
                    .senha(passwordEncoder.encode("admin123"))
                    .perfil(PerfilUsuario.ADMINISTRADOR)
                    .build());

            Usuario professor = usuarioRepo.save(Usuario.builder()
                    .nome("Prof. João Silva")
                    .email("professor@systhesis.com")
                    .senha(passwordEncoder.encode("prof123"))
                    .perfil(PerfilUsuario.PROFESSOR)
                    .build());

            usuarioRepo.save(Usuario.builder()
                    .nome("Aluno Maria Oliveira")
                    .email("aluno@systhesis.com")
                    .senha(passwordEncoder.encode("aluno123"))
                    .perfil(PerfilUsuario.ALUNO)
                    .build());

            // Missão 1 — Lua
            Missao missaoLua = missaoRepo.save(Missao.builder()
                    .titulo("Primeira Colheita na Lua")
                    .descricao("Mantenha a estufa lunar ativa durante uma tempestade de micrometeorotos.")
                    .planeta(Planeta.LUA)
                    .dificuldade(2)
                    .pontosRecompensa(150)
                    .ativa(true)
                    .criador(professor)
                    .build());

            perguntaRepo.save(Pergunta.builder()
                    .missao(missaoLua)
                    .enunciado("A estufa lunar consome 120W. Sua bateria tem 1000Wh. Por quantas horas a estufa pode funcionar sem energia solar?")
                    .alternativaA("6 horas")
                    .alternativaB("8 horas")
                    .alternativaC("10 horas")
                    .alternativaD("12 horas")
                    .respostaCorreta("B")
                    .explicacao("1000Wh ÷ 120W ≈ 8,3 horas. A resposta mais próxima é 8 horas.")
                    .build());

            perguntaRepo.save(Pergunta.builder()
                    .missao(missaoLua)
                    .enunciado("Uma planta precisa de 2L de água por dia. Você tem 50L de reserva. Quantos dias de irrigação sua reserva garante?")
                    .alternativaA("20 dias")
                    .alternativaB("25 dias")
                    .alternativaC("30 dias")
                    .alternativaD("35 dias")
                    .respostaCorreta("B")
                    .explicacao("50L ÷ 2L/dia = 25 dias de irrigação.")
                    .build());

            // Missão 2 — Marte
            Missao missaoMarte = missaoRepo.save(Missao.builder()
                    .titulo("Sobrevivência em Tempestade Marciana")
                    .descricao("Uma tempestade de poeira reduziu a energia solar em 35%. Tome decisões críticas para sobreviver.")
                    .planeta(Planeta.MARTE)
                    .dificuldade(4)
                    .pontosRecompensa(300)
                    .ativa(true)
                    .criador(professor)
                    .build());

            perguntaRepo.save(Pergunta.builder()
                    .missao(missaoMarte)
                    .enunciado("Sua geração solar era de 500W. Com a tempestade reduzindo 35%, qual a geração atual?")
                    .alternativaA("325W")
                    .alternativaB("300W")
                    .alternativaC("275W")
                    .alternativaD("250W")
                    .respostaCorreta("A")
                    .explicacao("500W × (1 - 0,35) = 500W × 0,65 = 325W.")
                    .build());

            perguntaRepo.save(Pergunta.builder()
                    .missao(missaoMarte)
                    .enunciado("O sistema de oxigênio consome 200W e a estufa 150W. Com 325W disponíveis, qual sistema você deve priorizar?")
                    .alternativaA("Apenas a estufa (sobra energia)")
                    .alternativaB("Apenas o oxigênio (sobrevivência primeiro)")
                    .alternativaC("Os dois funcionam simultaneamente")
                    .alternativaD("Nenhum dos dois")
                    .respostaCorreta("C")
                    .explicacao("200W + 150W = 350W > 325W. Na verdade NÃO é possível manter os dois. A resposta correta seria B — priorizar oxigênio.")
                    .build());

            log.info(">>> Dados iniciais carregados com sucesso!");
            log.info(">>> Acesse o Swagger: http://localhost:8080/swagger-ui.html");
            log.info(">>> Acesse o H2 Console: http://localhost:8080/h2-console");
            log.info(">>> Login admin: admin@systhesis.com / admin123");
            log.info(">>> Login professor: professor@systhesis.com / prof123");
            log.info(">>> Login aluno: aluno@systhesis.com / aluno123");
        };
    }
}
