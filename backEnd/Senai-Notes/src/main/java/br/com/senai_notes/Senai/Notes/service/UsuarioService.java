package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    //Post
    public Usuario criarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    //Get
    public Usuario buscarUsuarioPorId(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
    //Put
    public Usuario atualizarUsuario(Usuario usuario, Integer id){
       Usuario usuarioAntigo = buscarUsuarioPorId(id);
        if (id == null){
            return null;
        }
        usuarioAntigo.setEmail(usuario.getEmail());
        usuarioAntigo.setSenha(usuario.getSenha());

        usuarioRepository.save(usuarioAntigo);
        return usuarioAntigo;
    }
    //Delete
    public Usuario deletarUsuarioPorId(Integer id){
        Usuario user = buscarUsuarioPorId(id);
        if (user == null){
            return null;
        }
        usuarioRepository.deleteById(id);
        return user;
    }
}
