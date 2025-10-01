package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.TagAnotacoesDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.model.TagNota;
import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import br.com.senai_notes.Senai.Notes.repository.TagNotaRepository;
import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagNotaService {
    private final NotaRepository notaRepository;
    private final TagRepository tagRepository;
    private final TagNotaRepository tagNotaRepository;

    public TagNotaService(NotaRepository notaRepository, TagRepository tagRepository, TagNotaRepository tagNotaRepository){
        this.notaRepository = notaRepository;
        this.tagRepository = tagRepository;
        this.tagNotaRepository = tagNotaRepository;
    }
    // CREATE
    // Método para associar tag com nota
    public List<TagNota> associarTagENota(TagAnotacoesDto dto){
        List<TagNota> novasAssociacoes = new ArrayList<>();
        List<Tag> tagsAssociadas = tagRepository.findAllById(dto.getIdTag());
        Nota notasAssociada = notaRepository.findById(dto.getIdNota())
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada"));

        if(tagsAssociadas.size() != dto.getIdTag().size()){
            throw new ResourceNotFoundException("Pelo menos uma tag não foi encontrada");
        }

        for(Tag tag : tagsAssociadas){
                TagNota tagAnotacoes = new TagNota();
                tagAnotacoes.setNota(notasAssociada);
                tagAnotacoes.setTag(tag);
                novasAssociacoes.add(tagAnotacoes);

        }
        return tagNotaRepository.saveAll(novasAssociacoes);
    }

}
