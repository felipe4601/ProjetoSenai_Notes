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
}
