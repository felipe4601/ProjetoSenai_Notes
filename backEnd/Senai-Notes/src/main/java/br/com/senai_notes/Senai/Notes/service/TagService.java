package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.dtos.tag.CadastrarEditarTagDto;

import br.com.senai_notes.Senai.Notes.dtos.tag.ListarTagDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository  tagRepository;
    private final UsuarioRepository usuarioRepository;

    public TagService(TagRepository tagRepository,  UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Dtos
    // CREATE
    // Método para cadastrar tag usando dto
    public Tag cadastrarTagDto(CadastrarEditarTagDto dto){
        Tag novaTag = new Tag();
        novaTag.setNome(dto.getNome());
        Usuario usuarioAssciado = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        novaTag.setUsuario(usuarioAssciado);
        return tagRepository.save(novaTag);
    }

    // READ
    // Método para listar todas as tags
    public List<ListarTagDto> listarTodasTags(){
        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(this::converterParaListagemDto)
                .collect(Collectors.toList());
    }

    // Método para listar tags associadas a um usuario
    public List<ListarTagDto> listarTagsPorEmail(String email){
        List<Tag> tags = tagRepository.findByUsuarioEmailIgnoreCase(email);

        // faz a conversão do objeto tag para dto, pois são os dados que vou precisar
        return tags.stream()
                .map(this::converterParaListagemDto)
                .collect(Collectors.toList());
    }
    // Método para buscar tag por id
    public Tag buscarTagPorId(Integer id){
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag"));
    }

   // UPDATE
   // Método para atualizar tag
   public Tag atualizarTag(Integer id, CadastrarEditarTagDto dto){
        Tag tagExistente = buscarTagPorId(id);

        tagExistente.setNome(validacaoDeCampos(tagExistente.getNome(),dto.getNome()));
        if(dto.getEmail() != null){
            Usuario usuarioAssociado = usuarioRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            tagExistente.setUsuario(usuarioAssociado);
        }

        return tagRepository.save(tagExistente);
   }

   // DELETE
    // Método para deletar tag
    public Tag deletarTag(Integer id){
        Tag tagExistente = buscarTagPorId(id);
        tagRepository.delete(tagExistente);
        return tagExistente;
    }

    private ListarTagDto converterParaListagemDto(Tag tag){
        ListarTagDto dto = new ListarTagDto();
        ListarUsuarioDto usuarioDto = new ListarUsuarioDto();

        usuarioDto.setId(tag.getUsuario().getIdUsuario());
        usuarioDto.setEmail(tag.getUsuario().getEmail());

        dto.setId(tag.getIdTag());
        dto.setNome(tag.getNome());
        dto.setUsuario(usuarioDto);

        return dto;

    }

    private String validacaoDeCampos(String existente, String novo){
        if(novo != null && !novo.isBlank()){
            return novo;
        }
        return existente;
    }



}
