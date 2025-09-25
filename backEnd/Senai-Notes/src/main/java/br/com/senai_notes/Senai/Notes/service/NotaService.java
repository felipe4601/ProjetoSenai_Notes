package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import org.springframework.stereotype.Service;

@Service
public class NotaService {
    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository){
        this.notaRepository = notaRepository;
    }
}
