package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.anotacao.CadastrarEditarAnotacaoDto;
import br.com.senai_notes.Senai.Notes.dtos.anotacao.ListarAnotacoesDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.*;
import br.com.senai_notes.Senai.Notes.repository.*;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
        // Atualizando titulo
        notaExistente.setTitulo((dto.getTitulo()!=null && !dto.getTitulo().isBlank())
                ? dto.getTitulo() : notaExistente.getTitulo());
        // Atualizando descrição
        notaExistente.setDescricao((dto.getDescricao()!=null && !dto.getDescricao().isBlank())
                ? dto.getDescricao() : notaExistente.getDescricao());
        // Atualizando imagen
        notaExistente.setImagem((dto.getImagem()!=null && !dto.getImagem().isBlank())
                ? dto.getImagem() : notaExistente.getImagem());
        // Atualizando data de edição
        notaExistente.setDataEdicao(OffsetDateTime.now());
        // Atualizando estado da nota
        notaExistente.setEstadoNota((dto.getEstadoNota()!=null && !dto.getEstadoNota().isBlank())
            ? dto.getEstadoNota() : notaExistente.getEstadoNota());


        // Atualizando usuario
        if(dto.getEmail()!=null && !dto.getEmail().isBlank()){
            Usuario usuarioAssociado = usuarioRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
            notaExistente.setUsuario(usuarioAssociado);

        }
//        // Atualizando compartilhamento
//        if(dto.getCompartilhada()!=null && novaNota.getCompartilhada().getIdCompartilhada()!=null){
//            Integer idCompartilhamentoAssociado = novaNota.getCompartilhada().getIdCompartilhada();
//            Compartilhada compartilhamentoAssociado = compartilhadaRepository.findById(idCompartilhamentoAssociado)
//                    .orElseThrow(() -> new ResourceNotFoundException("Compartilhamento"));
//
//            notaExistente.setCompartilhada(compartilhamentoAssociado);
//        }
//        else {
//            notaExistente.setCompartilhada(null);
//        }

       return notaRepository.save(notaExistente);
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
    public Nota cadastrarTagAnotacaoDto(CadastrarEditarAnotacaoDto dto){
        Nota novaNota = new Nota();
        // Mapeando dados para a anotação
        novaNota.setTitulo(dto.getTitulo());
        novaNota.setDescricao(dto.getDescricao());
        novaNota.setImagem(dto.getImagem());
        novaNota.setDataEdicao(OffsetDateTime.now());
        novaNota.setEstadoNota(dto.getEstadoNota());
        novaNota.setDataCriacao(OffsetDateTime.now());
        Usuario usuarioAssociado = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        novaNota.setUsuario(usuarioAssociado);
        notaRepository.save(novaNota);
        List<TagNota> associacoes = new ArrayList<>();
        List<Tag> tagsAssociadas = tagRepository.findAllByNomeInAndUsuarioEmail(dto.getTags(), dto.getEmail());

        if(dto.getTags().size() != tagsAssociadas.size()){
            throw new ResourceNotFoundException("Tag");
        }
        for(Tag tags: tagsAssociadas){
            TagNota associacao = new TagNota();
            associacao.setTag(tags);
            associacao.setNota(novaNota);
            associacoes.add(associacao);
        }
        tagNotaRepository.saveAll(associacoes);
        return novaNota;
    }

    // READ
     //Método para listar anotarções
    public List<ListarAnotacoesDto> listarAnotacoesPorUsuario(String email) {
        List<Nota> notas = notaRepository.findByUsuarioEmail(email);
        List<ListarAnotacoesDto> anotacoes = new ArrayList<>();
        for (Nota nota : notas) {
            ListarAnotacoesDto anotacao = new ListarAnotacoesDto();
            anotacao.setId(nota.getIdNota());
            anotacao.setTitulo(nota.getTitulo());
            anotacao.setDescricao(nota.getDescricao());
            anotacao.setImagem(nota.getImagem());
            anotacao.setEstadoNota(nota.getEstadoNota());
            List<TagNota> associacoes = tagNotaRepository.findAllByNota(nota);

            List<String> tagsAssociadas = new ArrayList<>();
            for (TagNota associacaoNota : associacoes) {
                Tag tagAssociada = tagRepository.findById(associacaoNota.getTag().getIdTag())
                        .orElseThrow(() -> new ResourceNotFoundException("Tag"));
                tagsAssociadas.add(tagAssociada.getNome());
            }

            anotacao.setTag(tagsAssociadas);
            anotacoes.add(anotacao);
        }
        return anotacoes;
    }



    }





