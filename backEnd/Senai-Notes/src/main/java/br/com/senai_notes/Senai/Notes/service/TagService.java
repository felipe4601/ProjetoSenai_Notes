package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository  tagRepository;
    private final UsuarioRepository usuarioRepository;

    public TagService(TagRepository tagRepository,  UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // CRUD
    // CREATE
    // Método para cadastrar tag
    public Tag cadastrarTag(Tag novaTag){
       if(novaTag.getUsuario()!=null){
            Integer idUsuario = novaTag.getUsuario().getIdUsuario();
           Usuario usuarioAssociado = usuarioRepository.findById(idUsuario)
                   .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
       }

       return tagRepository.save(novaTag);
    }

}
