package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository  tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}
