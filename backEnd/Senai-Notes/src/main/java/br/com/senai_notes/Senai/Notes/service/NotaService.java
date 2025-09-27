package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;

    }


    // CRUD
    // CREATE
    // Método para cadastrar nota
    public Nota cadastrarNota(Nota novaNota){
        if(novaNota.getUsuario()!=null && novaNota.getUsuario().getIdUsuario() != null){
            Integer idUsuario = novaNota.getUsuario().getIdUsuario();
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
        }
        return  notaRepository.save(novaNota);


    }
    // READ
    // Método para listar por usuário
    public List<Nota> listarPorUsuario(String email){
        return notaRepository.findByUsuarioEmail(email);
    }

    // Método para buscar nota por usuário
    public Nota buscarPorId(Integer id){
        return notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota"));
    }

}
