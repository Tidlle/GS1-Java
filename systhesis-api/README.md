# Systhesis API

> **API REST para Simulação Gamificada de Gestão de Recursos em Colônias Espaciais**

Projeto desenvolvido para a disciplina de **Java Advanced — Global Solution 2025** da **FIAP**.

---

## Índice

- [Descrição do Projeto](#-descrição-do-projeto)
- [Objetivo da Solução](#-objetivo-da-solução)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Modelagem Avançada](#-modelagem-avançada)
- [Segurança](#-segurança)
- [Endpoints Principais](#-endpoints-principais)
- [Como Executar Localmente](#-como-executar-localmente)
- [Como Testar a API](#-como-testar-a-api)
- [Usuários de Teste](#-usuários-de-teste)
- [Links Importantes](#-links-importantes)
- [Integrantes](#-integrantes)
- [Requisitos Atendidos](#-requisitos-atendidos)

---

## Descrição do Projeto

O **Systhesis** é uma API REST educacional e gamificada em que o aluno assume o papel de **comandante de uma colônia espacial** na Lua ou em Marte. Para manter a base em operação, ele deve administrar recursos essenciais como água, energia, oxigênio, alimento e temperatura, além de responder a desafios científicos propostos por missões educacionais.

A cada missão concluída, a colônia acumula pontos, subindo no ranking global. Eventos aleatórios — como tempestades solares, falhas energéticas, vazamentos de água e perdas de colheita — desafiam o aluno a tomar decisões críticas, simulando situações reais de gestão de recursos em ambientes extremos.

A API expõe todos os recursos necessários para que uma aplicação front-end (web ou mobile) consuma os dados e implemente a experiência gamificada completa.

---

## Objetivo da Solução

O Systhesis tem como objetivo estimular:

- **Aprendizado por simulação** — o aluno aprende conceitos de física, biologia e sustentabilidade ao gerir recursos reais de uma colônia espacial.
- **Tomada de decisão** — eventos inesperados exigem priorização de recursos sob pressão.
- **Raciocínio lógico e matemático** — as perguntas das missões exigem cálculo e raciocínio aplicado.
- **Gestão de recursos e sustentabilidade** — escassez de água, energia e alimento são desafios reais transpostos para o contexto espacial.
- **Engajamento tecnológico** — a gamificação aumenta a motivação e a retenção de conteúdo em ambientes educacionais.

---

## Funcionalidades Principais

| Funcionalidade | Descrição |
|---|---|
| **Cadastro e Login** | Criação de conta com perfil ALUNO, PROFESSOR ou ADMINISTRADOR |
| **Autenticação JWT** | Token Bearer gerado no login e exigido nos endpoints protegidos |
| **Gerenciamento de Colônias** | CRUD completo para criação e administração de bases espaciais |
| **Controle de Recursos** | Acompanhamento em tempo real de água, energia, oxigênio, alimento e temperatura |
| **Eventos de Impacto** | Quatro tipos de eventos que reduzem recursos específicos da colônia |
| **Missões Educacionais** | Desafios com perguntas de múltipla escolha vinculados a planetas e dificuldades |
| **Tentativas de Resposta** | Registro das respostas dos alunos com correção automática |
| **Sistema de Pontuação** | Respostas corretas adicionam pontos à colônia do aluno |
| **Ranking Global** | Classificação pública das colônias ordenadas por pontuação total |
| **Documentação Swagger** | Interface interativa para exploração e teste de todos os endpoints |

---

## Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 3.2.5 | Framework principal |
| **Spring Web** | — | Criação de endpoints REST |
| **Spring Data JPA** | — | Persistência e repositórios |
| **Spring Security** | — | Autenticação e autorização |
| **JJWT** | 0.11.5 | Geração e validação de tokens JWT |
| **Spring Validation** | — | Validação de dados de entrada |
| **Lombok** | — | Redução de boilerplate (getters, builders, etc.) |
| **Spring Boot DevTools** | — | Recarregamento automático em desenvolvimento |
| **Springdoc OpenAPI** | 2.5.0 | Documentação Swagger/OpenAPI |
| **H2 Database** | — | Banco de dados em memória para testes e deploy |
| **Maven** | 3.x | Gerenciamento de dependências e build |
| **Render** | — | Plataforma de deploy público da API |

---

## Arquitetura do Projeto

O projeto segue a **arquitetura em camadas** padrão do ecossistema Spring Boot, garantindo separação de responsabilidades e facilidade de manutenção.

```
controller   →  recebe requisições HTTP, delega ao service, retorna DTOs
service      →  contém a lógica de negócio e orquestra as operações
repository   →  acesso ao banco de dados via Spring Data JPA
entity       →  mapeamento das tabelas do banco com anotações JPA
dto          →  objetos de transferência de dados (request e response)
enums        →  tipos enumerados (Planeta, TipoRecurso, StatusColonia, PerfilUsuario)
exception    →  exceções customizadas e handler global de erros
security     →  filtro JWT e serviço de geração/validação de tokens
config       →  configurações de segurança, CORS, OpenAPI e dados iniciais
```

### Estrutura de Pastas

```
systhesis-api/
├── src/
│   └── main/
│       ├── java/br/com/fiap/systhesis/
│       │   ├── config/
│       │   │   ├── CorsConfig.java
│       │   │   ├── DataLoader.java
│       │   │   ├── OpenApiConfig.java
│       │   │   └── SecurityConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── ColoniaController.java
│       │   │   ├── EventoController.java
│       │   │   ├── HomeController.java
│       │   │   ├── MissaoController.java
│       │   │   ├── RankingController.java
│       │   │   └── TentativaController.java
│       │   ├── dto/
│       │   │   ├── CadastroUsuarioRequest.java
│       │   │   ├── ColoniaRequest.java
│       │   │   ├── ColoniaResponse.java
│       │   │   ├── ErroResponse.java
│       │   │   ├── EventoRequest.java
│       │   │   ├── EventoResponse.java
│       │   │   ├── LocalizacaoResponse.java
│       │   │   ├── LoginRequest.java
│       │   │   ├── MissaoRequest.java
│       │   │   ├── MissaoResponse.java
│       │   │   ├── RecursoColoniaResponse.java
│       │   │   ├── TentativaRequest.java
│       │   │   ├── TentativaResponse.java
│       │   │   ├── TokenResponse.java
│       │   │   └── UsuarioResponse.java
│       │   ├── entity/
│       │   │   ├── Colonia.java
│       │   │   ├── Conquista.java
│       │   │   ├── Evento.java              ← classe abstrata (herança JPA)
│       │   │   ├── FalhaEnergetica.java
│       │   │   ├── LocalizacaoEspacial.java ← @Embeddable
│       │   │   ├── Missao.java
│       │   │   ├── Pergunta.java
│       │   │   ├── PerdaColheita.java
│       │   │   ├── RecursoColonia.java      ← chave composta @EmbeddedId
│       │   │   ├── RecursoColoniaId.java    ← @Embeddable (chave composta)
│       │   │   ├── TempestadeSolar.java
│       │   │   ├── Tentativa.java
│       │   │   ├── Usuario.java
│       │   │   └── VazamentoAgua.java
│       │   ├── enums/
│       │   │   ├── PerfilUsuario.java
│       │   │   ├── Planeta.java
│       │   │   ├── StatusColonia.java
│       │   │   └── TipoRecurso.java
│       │   ├── exception/
│       │   │   ├── CredenciaisInvalidasException.java
│       │   │   ├── EmailJaCadastradoException.java
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── RecursoNaoEncontradoException.java
│       │   ├── repository/
│       │   │   ├── ColoniaRepository.java
│       │   │   ├── EventoRepository.java
│       │   │   ├── MissaoRepository.java
│       │   │   ├── PerguntaRepository.java
│       │   │   ├── RecursoColoniaRepository.java
│       │   │   ├── TentativaRepository.java
│       │   │   └── UsuarioRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java
│       │   │   └── JwtService.java
│       │   └── service/
│       │       ├── AuthService.java
│       │       ├── ColoniaService.java
│       │       ├── EventoService.java
│       │       ├── MissaoService.java
│       │       └── TentativaService.java
│       └── resources/
│           └── application.properties
├── Dockerfile
└── pom.xml
```

---

## Modelagem Avançada

O projeto implementa quatro requisitos de modelagem avançada com JPA:

### 1. Herança JPA — `SINGLE_TABLE`
A entidade `Evento` é uma **classe abstrata** que utiliza `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`. Cada tipo de evento é uma subclasse com seu próprio `@DiscriminatorValue`:

| Subclasse | Discriminador | Recurso Afetado |
|---|---|---|
| `TempestadeSolar` | `TEMPESTADE_SOLAR` | Energia |
| `FalhaEnergetica` | `FALHA_ENERGETICA` | Energia |
| `VazamentoAgua` | `VAZAMENTO_AGUA` | Água |
| `PerdaColheita` | `PERDA_COLHEITA` | Alimento |

### 2. Classe Embeddable — `LocalizacaoEspacial`
A localização da colônia é modelada como `@Embeddable`, sendo incorporada diretamente na tabela `tb_colonia`. Contém: `planeta`, `setor`, `latitude` e `longitude`.

### 3. Chave Composta — `RecursoColoniaId`
A tabela `tb_recurso_colonia` utiliza `@EmbeddedId` com a classe `RecursoColoniaId`, composta por `coloniaId` + `tipoRecurso`. Isso garante que cada colônia tenha exatamente um registro por tipo de recurso.

### 4. Múltiplas Tabelas e Relacionamentos
O modelo conta com **8 tabelas** com relacionamentos `@ManyToOne` e `@OneToMany`, todos com `FetchType.LAZY` para otimização de performance.

---

## Segurança

A API utiliza **Spring Security** com autenticação stateless baseada em **JWT (JSON Web Token)**.

### Fluxo de autenticação

```
Cliente → POST /auth/login → AuthService → JWT gerado → retornado ao cliente
Cliente → GET /colonias (Header: Authorization: Bearer <token>) → JwtAuthFilter → valida token → acesso liberado
```

### Perfis de usuário

| Perfil | Permissões |
|---|---|
| `ALUNO` | Criar colônias, registrar tentativas, consultar missões e ranking |
| `PROFESSOR` | Tudo do ALUNO + criar, editar e excluir missões |
| `ADMINISTRADOR` | Acesso completo a todos os recursos |

### Endpoints públicos (sem autenticação)

- `GET /` — status da API
- `POST /auth/login` e `POST /auth/cadastro`
- `GET /missoes/**` — leitura de missões
- `GET /ranking` — ranking público
- `GET /colonias/**`, `GET /eventos/**`, `GET /tentativas/**` — leitura pública para fins de teste
- `/swagger-ui/**`, `/api-docs/**`, `/h2-console/**` — documentação

---

## Endpoints Principais

### Autenticação

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/auth/login` | ❌ Pública | Autentica e retorna token JWT |
| `POST` | `/auth/cadastro` | ❌ Pública | Cria novo usuário |

**Exemplo de body — login:**
```json
{
  "email": "aluno@systhesis.com",
  "senha": "aluno123"
}
```

**Exemplo de resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "email": "aluno@systhesis.com",
  "perfil": "ALUNO"
}
```

---

### Colônias

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/colonias` | ✅ JWT | Cria nova colônia para o usuário autenticado |
| `GET` | `/colonias` | ✅ JWT | Lista as colônias do usuário autenticado |
| `GET` | `/colonias/{id}` | ✅ JWT | Busca colônia por ID |
| `PUT` | `/colonias/{id}` | ✅ JWT | Atualiza dados da colônia |
| `DELETE` | `/colonias/{id}` | ✅ JWT | Exclui a colônia |
| `GET` | `/colonias/{id}/recursos` | ✅ JWT | Lista os recursos da colônia |

**Exemplo de body — criar colônia:**
```json
{
  "nome": "Base Alfa",
  "planeta": "MARTE",
  "setor": "A-7",
  "latitude": -14.5,
  "longitude": 32.1
}
```

---

### ⚡ Eventos

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/eventos` | ✅ JWT | Cria evento e aplica impacto nos recursos |
| `GET` | `/eventos` | ✅ JWT | Lista todos os eventos (ou filtra por `?coloniaId=`) |
| `GET` | `/eventos/{id}` | ✅ JWT | Busca evento por ID |
| `PUT` | `/eventos/{id}` | ✅ JWT | Atualiza evento (tipo não pode ser alterado) |
| `DELETE` | `/eventos/{id}` | ✅ JWT | Exclui evento |

**Exemplo de body — criar evento:**
```json
{
  "titulo": "Tempestade Solar Intensa",
  "descricao": "A tempestade reduziu a eficiência dos painéis solares.",
  "coloniaId": 1,
  "tipoEvento": "TEMPESTADE_SOLAR",
  "impactoPercentual": 20.0
}
```

Tipos válidos: `TEMPESTADE_SOLAR`, `FALHA_ENERGETICA`, `VAZAMENTO_AGUA`, `PERDA_COLHEITA`

---

### Tentativas

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `POST` | `/tentativas` | ✅ JWT | Registra resposta e pontua a colônia se correta |
| `GET` | `/tentativas` | ✅ JWT | Lista tentativas (filtra por `?usuarioId=` ou `?coloniaId=`) |
| `GET` | `/tentativas/{id}` | ✅ JWT | Busca tentativa por ID |
| `PUT` | `/tentativas/{id}` | ✅ JWT | Atualiza resposta e recalcula pontuação |
| `DELETE` | `/tentativas/{id}` | ✅ JWT | Exclui e remove pontuação da colônia |

**Exemplo de body — registrar tentativa:**
```json
{
  "perguntaId": 1,
  "coloniaId": 1,
  "respostaEnviada": "B"
}
```

---

### Missões

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `GET` | `/missoes` | ❌ Pública | Lista missões ativas |
| `GET` | `/missoes/{id}` | ❌ Pública | Busca missão por ID |
| `POST` | `/missoes` | ✅ PROFESSOR/ADMIN | Cria nova missão |
| `PUT` | `/missoes/{id}` | ✅ PROFESSOR/ADMIN | Atualiza missão |
| `DELETE` | `/missoes/{id}` | ✅ PROFESSOR/ADMIN | Desativa missão (soft delete) |

---

### Ranking

| Método | Endpoint | Autenticação | Descrição |
|---|---|---|---|
| `GET` | `/ranking` | ❌ Pública | Lista colônias ordenadas por pontuação total |

---

## Como Executar Localmente

### Pré-requisitos
- Java 21+
- Maven 3.8+ (ou utilize o `mvnw` incluído no projeto)
- Git

### Passo a passo

```bash
# 1. Clonar o repositório
git clone https://github.com/Tidlle/GS1-Java.git

# 2. Entrar na pasta do projeto
cd GS1-Java
cd systhesis-api

# 3. Compilar e empacotar
./mvnw clean package -DskipTests
# ou no Windows:
mvnw.cmd clean package -DskipTests

# 4. Iniciar a aplicação
./mvnw spring-boot:run
# ou no Windows:
mvnw.cmd spring-boot:run
```

### Acessos locais

| Recurso | URL |
|---|---|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

> **H2 Console:** JDBC URL: `jdbc:h2:mem:systhesisdb` · Usuário: `sa` · Senha: *(em branco)*

---

## Como Testar a API

Acesse o Swagger em: **https://gs1-java-hikm.onrender.com/swagger-ui/index.html#/**

### Sequência recomendada de testes

#### 1. Autenticar-se
```
POST /auth/login
Body: { "email": "aluno@systhesis.com", "senha": "aluno123" }
→ Copie o valor do campo "token"
```

#### 2. Autorizar no Swagger
```
Clique em "Authorize" (cadeado) → cole: Bearer <token_copiado> → Authorize
```

#### 3. Criar uma colônia
```
POST /colonias
Body: { "nome": "Base Alfa", "planeta": "MARTE", "setor": "A-7" }
→ Anote o "id" retornado
```

#### 4. Listar colônias
```
GET /colonias
→ Deve retornar a colônia criada com usuarioId (sem dados do usuário)
```

#### 5. Consultar recursos da colônia
```
GET /colonias/{id}/recursos
→ Deve retornar os 5 recursos inicializados: AGUA, ENERGIA, OXIGENIO, ALIMENTO, TEMPERATURA
```

#### 6. Criar um evento de impacto
```
POST /eventos
Body: { "titulo": "Tempestade Solar", "coloniaId": 1, "tipoEvento": "TEMPESTADE_SOLAR", "impactoPercentual": 20.0 }
→ O recurso ENERGIA da colônia será reduzido em 20%
```

#### 7. Verificar impacto nos recursos
```
GET /colonias/{id}/recursos
→ Confirme que o valor de ENERGIA foi reduzido
```

#### 8. Consultar missões disponíveis
```
GET /missoes
→ Lista missões com seus IDs de perguntas
```

#### 9. Registrar uma tentativa de resposta
```
POST /tentativas
Body: { "perguntaId": 1, "coloniaId": 1, "respostaEnviada": "B" }
→ Se correta, a pontuação da colônia é atualizada
```

#### 10. Conferir o ranking
```
GET /ranking
→ Colônias ordenadas por pontuação total (público, sem token)
```

---

## Usuários de Teste

Os seguintes usuários são criados automaticamente pelo `DataLoader` na inicialização da aplicação:

| Perfil | E-mail | Senha | Permissões |
|---|---|---|---|
| `ADMINISTRADOR` | `admin@systhesis.com` | `admin123` | Acesso total |
| `PROFESSOR` | `professor@systhesis.com` | `prof123` | Criar/editar missões |
| `ALUNO` | `aluno@systhesis.com` | `aluno123` | Jogar e registrar tentativas |

>  O banco de dados é **em memória (H2)**. Os dados são recriados a cada reinício da aplicação no deploy do Render.

---

## Links Importantes

| Recurso | URL |
|---|---|
| 🌐 Deploy da API | https://gs1-java-hikm.onrender.com |
| 📄 Swagger / OpenAPI | https://gs1-java-hikm.onrender.com/swagger-ui/index.html#/ |
| 🎥 Vídeo de Apresentação | COLOCAR_LINK_DO_VIDEO_AQUI |
| 💻 Repositório GitHub | COLOCAR_LINK_DO_GITHUB_AQUI |

> **Aviso sobre o Render (plano gratuito):** O servidor pode entrar em modo de hibernação após 15 minutos de inatividade. A primeira requisição após esse período pode levar até 60 segundos para responder — aguarde e tente novamente.

---

## Integrantes

| Nome | RM |
|---|---|
| Eduardo Martins | RM562259 |
| Joao Victor Alcantara | RM562707 |
| Phillipo Barbosa | RM565399 |

---

> Projeto desenvolvido para fins acadêmicos — **Global Solution 2025 · Java Advanced · FIAP**
