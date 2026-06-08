package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.dto.ColoniaRequest;
import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.entity.LocalizacaoEspacial;
import br.com.fiap.systhesis.entity.RecursoColonia;
import br.com.fiap.systhesis.entity.RecursoColoniaId;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.enums.StatusColonia;
import br.com.fiap.systhesis.enums.TipoRecurso;
import br.com.fiap.systhesis.exception.RecursoNaoEncontradoException;
import br.com.fiap.systhesis.repository.ColoniaRepository;
import br.com.fiap.systhesis.repository.RecursoColoniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColoniaService {

    private final ColoniaRepository coloniaRepository;
    private final RecursoColoniaRepository recursoColoniaRepository;

    // ── Nível calculado com base no XP ───────────────────────────────────────────
    // xp   0–99   → nível 1
    // xp 100–299  → nível 2
    // xp 300–599  → nível 3
    // xp 600–999  → nível 4
    // xp ≥ 1000   → nível 5
    public static int calcularNivel(int xp) {
        if (xp >= 1000) return 5;
        if (xp >= 600)  return 4;
        if (xp >= 300)  return 3;
        if (xp >= 100)  return 2;
        return 1;
    }

    @Transactional
    public Colonia criar(ColoniaRequest request, Usuario usuario) {
        Colonia colonia = Colonia.builder()
                .nome(request.nome())
                .localizacao(LocalizacaoEspacial.builder()
                        .planeta(request.planeta())
                        .setor(request.setor())
                        .latitude(request.latitude())
                        .longitude(request.longitude())
                        .build())
                .status(StatusColonia.ATIVA)
                // recursos diretos (mobile)
                .agua(70)
                .energia(80)
                .oxigenio(90)
                .alimento(40)
                .temperatura(22)
                // progressão
                .nivel(1)
                .xp(0)
                .pontuacaoTotal(0)
                .usuario(usuario)
                .build();

        colonia = coloniaRepository.save(colonia);

        // Inicializa RecursoColonia (modelagem avançada: chave composta @EmbeddedId)
        inicializarRecursosColonia(colonia);

        return coloniaRepository.findById(colonia.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada após criação."));
    }

    /**
     * Popula a tabela tb_recurso_colonia com chave composta (coloniaId + tipoRecurso).
     * Mantido para satisfazer o requisito de modelagem avançada de Java Advanced.
     */
    private void inicializarRecursosColonia(Colonia colonia) {
        Arrays.stream(TipoRecurso.values()).forEach(tipo -> {
            double quantidade = switch (tipo) {
                case AGUA        -> 70.0;
                case ENERGIA     -> 80.0;
                case OXIGENIO    -> 90.0;
                case ALIMENTO    -> 40.0;
                case TEMPERATURA -> 22.0;
            };
            double maximo = tipo == TipoRecurso.TEMPERATURA ? 40.0 : 100.0;

            RecursoColonia recurso = RecursoColonia.builder()
                    .id(new RecursoColoniaId(colonia.getId(), tipo))
                    .colonia(colonia)
                    .quantidade(quantidade)
                    .quantidadeMaxima(maximo)
                    .build();
            recursoColoniaRepository.save(recurso);
        });
    }

    @Transactional(readOnly = true)
    public Colonia buscarPorId(Long id) {
        return coloniaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Colonia> listarPorUsuario(Long usuarioId) {
        return coloniaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Colonia> ranking() {
        return coloniaRepository.findRanking();
    }

    @Transactional
    public Colonia atualizar(Long id, ColoniaRequest request) {
        Colonia colonia = buscarPorId(id);
        // Atualiza apenas nome e localização — recursos, XP e nível são preservados
        colonia.setNome(request.nome());
        colonia.setLocalizacao(LocalizacaoEspacial.builder()
                .planeta(request.planeta())
                .setor(request.setor())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build());
        return coloniaRepository.save(colonia);
    }

    @Transactional
    public void deletar(Long id) {
        coloniaRepository.delete(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public List<RecursoColonia> listarRecursos(Long coloniaId) {
        buscarPorId(coloniaId); // valida existência
        return recursoColoniaRepository.findByColoniaId(coloniaId);
    }
}
