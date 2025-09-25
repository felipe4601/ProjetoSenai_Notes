package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
}
