package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.model.TagNota;
import br.com.senai_notes.Senai.Notes.repository.TagNotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagNotaService {
    private final TagNotaRepository tagNotaRepository;

    public TagNotaService(TagNotaRepository tagNotaRepository) {
        this.tagNotaRepository = tagNotaRepository;
    }

    //Create
    public TagNota criarTagNota(TagNota tagNota){
       return tagNotaRepository.save(tagNota);
    }
    //Read
    public List<TagNota> buscarTagNota(){
        return tagNotaRepository.findAll();
    }
    //Update

    //Delete
}
