package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.anotacao.CadastrarEditarAnotacaoDto;
import br.com.senai_notes.Senai.Notes.dtos.anotacao.ListarAnotacaoDto;
import br.com.senai_notes.Senai.Notes.dtos.tag.ListarTagDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.CadastrarEditarUsuarioDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.*;
import br.com.senai_notes.Senai.Notes.repository.*;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompartilhadaRepository compartilhadaRepository;
   private final TagRepository tagRepository;
   private final TagNotaRepository tagNotaRepository;


    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository,
                       CompartilhadaRepository compartilhadaRepository,
                       TagRepository tagRepository, TagNotaRepository tagNotaRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
        this.compartilhadaRepository = compartilhadaRepository;
        this.tagRepository = tagRepository;
        this.tagNotaRepository = tagNotaRepository;
    }


    // CREATE
    // Método para cadastrar nota
    // CRUD
    public Nota cadastrarNota(Nota novaNota){
        if(novaNota.getUsuario()!=null && novaNota.getUsuario().getIdUsuario() != null){
            Integer idUsuario = novaNota.getUsuario().getIdUsuario();
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        }
        novaNota.setDataCriacao(OffsetDateTime.now());
        novaNota.setDataEdicao(OffsetDateTime.now());
        return  notaRepository.save(novaNota);


    }

    // READ
    // Método para listar por usuário
    public List<Nota> listarPorUsuario(String email){
        return notaRepository.findByUsuarioEmail(email);
    }




    // Método para buscar nota por usuário
    public Nota buscarPorId(Integer id){
        return notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota"));
    }

    // UPDATE
    // Método para atualizar cadastro
    public Nota atualizarNota(Integer id, CadastrarEditarAnotacaoDto dto){
        Nota notaExistente = buscarPorId(id);
        // Atualizar usuário
        if(dto.getUsuarioId()!=null){
            Usuario usuarioAssociado = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            notaExistente.setUsuario(usuarioAssociado);
        }
        // Atualizando titulo
        notaExistente.setTitulo(validacaoDeCampos(notaExistente.getTitulo(),dto.getTitulo()));
        // Atualizando descrição
        notaExistente.setDescricao(validacaoDeCampos(notaExistente.getDescricao(),dto.getDescricao()));
        // Atualizando imagen
        notaExistente.setImagem(validacaoDeCampos(notaExistente.getImagem(), dto.getImagem()));
        // Atualizando data de edição
        notaExistente.setDataEdicao(OffsetDateTime.now());

        // Atualizei a nota
       notaRepository.save(notaExistente);

       // Agora eu vou atualzar as tags que estão associadas a ela
        List<Tag> tagsExistentes = tagRepository.findAllByNomeInAndUsuarioIdUsuario(dto.getTags(), dto.getUsuarioId());
        Set<String> nomesDasTagsExistentes = tagsExistentes.stream()
                .map(Tag::getNome)
                .collect(Collectors.toSet());

        List<String> nomesDasTagsNovas = dto.getTags().stream()
                .filter(nome -> !nomesDasTagsExistentes.contains(nome))
                .toList();
        // Criando as novas tags
        List<Tag> tagsSalvas = new ArrayList<>();
        if(!nomesDasTagsNovas.isEmpty()){
            List<Tag> tagsParaSalvar = nomesDasTagsNovas.stream()
                    .map(nomeTag -> {
                        Tag novaTag = new Tag();
                        novaTag.setNome(nomeTag);
                        novaTag.setUsuario(notaExistente.getUsuario());
                        return novaTag;
                    })
                    .toList();
            tagsSalvas =  tagRepository.saveAll(tagsParaSalvar);
        }
        tagsExistentes.addAll(tagsSalvas);



        return notaExistente;
    }




    // DELETE
    // Método para excluir usuário
    public Nota removerNota(Integer id){
        Nota notaAssociada = buscarPorId(id);
        notaRepository.delete(notaAssociada);
        return notaAssociada;
    }

    // DTOS
    // CREATE
    // Método para cadastrar anotação com associação de tag e usuário
    public CadastrarEditarAnotacaoDto cadastrarAnotacaoDto(CadastrarEditarAnotacaoDto dto){
        Nota novaNota = new Nota();
        Usuario usuarioAssociado = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        novaNota.setUsuario(usuarioAssociado);

        novaNota.setUsuario(usuarioAssociado);
        novaNota.setTitulo(dto.getTitulo());
        novaNota.setDescricao(dto.getDescricao());
        novaNota.setImagem(dto.getImagem());
        novaNota.setDataCriacao(OffsetDateTime.now());
        novaNota.setDataEdicao(OffsetDateTime.now());
        novaNota.setEstadoNota(true);

        notaRepository.save(novaNota);

        List<Tag> tagsExistentes = tagRepository.findAllByNomeInAndUsuarioIdUsuario(dto.getTags(), dto.getUsuarioId());
        Set<String> nomesDasTagsExistentes = tagsExistentes.stream()
                .map(Tag::getNome)
                .collect(Collectors.toSet());

        List<String> nomesDasNovasTags = dto.getTags().stream()
                .filter(nome -> !nomesDasTagsExistentes.contains(nome))
                .toList();
        // Criando as novas tags
        List<Tag> tagsSalvas = new ArrayList<>();
        if(!nomesDasNovasTags.isEmpty()){
            List<Tag> tagsParaSalvar = nomesDasNovasTags.stream()
                    .map(nomeTag -> {
                        Tag novaTag = new Tag();
                        novaTag.setNome(nomeTag);
                        novaTag.setUsuario(usuarioAssociado);
                        return novaTag;
                    })
                    .toList();
            tagsSalvas =  tagRepository.saveAll(tagsParaSalvar);
        }
        tagsExistentes.addAll(tagsSalvas);

        if(!tagsExistentes.isEmpty()){
            List<TagNota> associacoes = tagsExistentes.stream()
                    .map(associacao -> {
                        TagNota novaAssociacao = new TagNota();
                        novaAssociacao.setNota(novaNota);
                        novaAssociacao.setTag(associacao);
                        return novaAssociacao;
                    })
                    .toList();
            tagNotaRepository.saveAll(associacoes);
        }

        return dto;
    }

    // READ
     //Método para listar anotarções
    public List<ListarAnotacaoDto> listarAnotacoesPorUsuario(String email) {
        List<Nota> notas = notaRepository.findByUsuarioEmailCompleto(email);
        List<ListarAnotacaoDto> anotacoes = new ArrayList<>();

        return notas.stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());

    }




    private ListarAnotacaoDto converterParaDto(Nota nota){

        ListarAnotacaoDto dto = new ListarAnotacaoDto();

        dto.setId(nota.getIdNota());
        dto.setTitulo(nota.getTitulo());
        dto.setDescricao(nota.getDescricao());
        dto.setImagem(nota.getImagem());
        dto.setDataCriacao(OffsetDateTime.now());
        dto.setDataEdicao(OffsetDateTime.now());


        List<ListarTagDto> tagsDto = nota.getTagAnotacao().stream()
                .map(associacao -> converterTagParaDto(associacao.getTag()))
                .toList();
        return dto;
    }

    private ListarTagDto converterTagParaDto(Tag tag){
        ListarTagDto dto = new ListarTagDto();
        dto.setId(tag.getIdTag());
        dto.setNome(tag.getNome());
        return dto;
    }

    private String validacaoDeCampos(String existente, String novo){
        if(novo != null && !novo.isBlank()){
            return novo;
        }
        return existente;
    }



}





