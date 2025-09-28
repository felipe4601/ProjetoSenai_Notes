package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.dto.TagAnotacoesDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Tag;

import br.com.senai_notes.Senai.Notes.model.TagNota;
import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import br.com.senai_notes.Senai.Notes.repository.TagNotaRepository;
import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagNotaService {
    private final TagNotaRepository tagNotaRepository;
    private final TagRepository tagRepository;
    private final NotaRepository notaRepository;

    public TagNotaService(TagNotaRepository tagNotaRepository, TagRepository tagRepository, NotaRepository notaRepository) {
        this.tagNotaRepository = tagNotaRepository;
        this.tagRepository = tagRepository;
        this.notaRepository = notaRepository;
    }

    //Creat
    public TagNota criarTagNota(TagNota tagNota){
       return tagNotaRepository.save(tagNota);
    }
    // TagAnotações Dto
    public TagNota associarTagENota(TagAnotacoesDto dto){
        Tag tagAssociada = tagRepository.findById(dto.getIdTag())
                .orElseThrow(() -> new ResourceNotFoundException("Tag"));
        Nota notaAssociada = notaRepository.findById(dto.getIdNota())
                .orElseThrow(() -> new ResourceNotFoundException("Nota"));
        TagNota tagAnotacoes = new  TagNota();
        tagAnotacoes.setIdTag(tagAssociada.getIdTag());
        tagAnotacoes.setIdNota(notaAssociada.getIdNota());

        return tagNotaRepository.save(tagAnotacoes);
    }
    //Read

    //Update

    //Delete
}
