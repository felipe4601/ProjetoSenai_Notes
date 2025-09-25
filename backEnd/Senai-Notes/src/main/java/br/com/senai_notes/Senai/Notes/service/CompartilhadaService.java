package br.com.senai_notes.Senai.Notes.service;

import br.com.senai_notes.Senai.Notes.model.Compartilhada;
import br.com.senai_notes.Senai.Notes.repository.CompartilhadaRepository;
import org.springframework.stereotype.Service;

@Service
public class CompartilhadaService {

    private final CompartilhadaRepository compartilhadaRepository;

    public CompartilhadaService(CompartilhadaRepository comp) {
        compartilhadaRepository = comp;
    }

    //Post - Creat
    public Compartilhada criarCompartilhada(Compartilhada compartilhada){
        return compartilhadaRepository.save(compartilhada);
    }

    //Get - Read
    public Compartilhada buscarCompartilhadaPorId(Integer id){
        return compartilhadaRepository.findById(id).orElse(null);
    }

    //Put - Update

    //Delet - Delet
}
