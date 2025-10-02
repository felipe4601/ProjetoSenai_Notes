package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {
    private final TagRepository  tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

<<<<<<< HEAD
    //Create
    public Tag criarTag(Tag tag){
        return tagRepository.save(tag);
    }
    //Read
    public List<Tag> buscarTagNota(){
        return tagRepository.findAll();
    }
    //Update

    //Delete
=======
    // CRUD
    // CREATE
    // Método para cadastrar tag
//    public Tag cadastrarTag(Tag novaTag){
//        if(novaTag.getUsuario()!=null && novaTag.getUsuario().getIdUsuario()!=null)
//    }
>>>>>>> f415e0ff5d851d7c6586f81ec40a2e8c6e416fdb
}
