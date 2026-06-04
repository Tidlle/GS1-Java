package br.com.fiap.systhesis.service;

import br.com.fiap.systhesis.entity.Colonia;
import br.com.fiap.systhesis.entity.LocalizacaoEspacial;
import br.com.fiap.systhesis.entity.RecursoColonia;
import br.com.fiap.systhesis.entity.RecursoColoniaId;
import br.com.fiap.systhesis.entity.Usuario;
import br.com.fiap.systhesis.enums.StatusColonia;
import br.com.fiap.systhesis.enums.TipoRecurso;
import br.com.fiap.systhesis.dto.ColoniaRequest;
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
                .pontuacaoTotal(0)
                .usuario(usuario)
                .build();

        colonia = coloniaRepository.save(colonia);

        // Inicializar recursos com valores padrão
        inicializarRecursos(colonia);

        return coloniaRepository.findById(colonia.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colônia não encontrada após criação."));
    }

    private void inicializarRecursos(Colonia colonia) {
        double[][] recursos = {
            // TipoRecurso.ordinal -> {quantidade, maximo}
        };

        Arrays.stream(TipoRecurso.values()).forEach(tipo -> {
            double quantidade = switch (tipo) {
                case AGUA -> 70.0;
                case ENERGIA -> 80.0;
                case OXIGENIO -> 90.0;
                case ALIMENTO -> 40.0;
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
        colonia.setNome(request.nome());
        colonia.setLocalizacao(LocalizacaoEspacial.builder()
                .planeta(request.planeta())
                .setor(request.setor())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build());
        return coloniaRepository.save(colonia);
    }

    public void deletar(Long id) {
        Colonia colonia = buscarPorId(id);
        coloniaRepository.delete(colonia);
    }

    @Transactional(readOnly = true)
    public List<RecursoColonia> listarRecursos(Long coloniaId) {
        buscarPorId(coloniaId); // valida existência
        return recursoColoniaRepository.findByColoniaId(coloniaId);
    }
}
