package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.usuario.CadastrarEditarUsuarioDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    //Post
    public Usuario criarUsuario(CadastrarEditarUsuarioDto usuario){
        Usuario usuarioCriado = new Usuario();

        usuarioCriado.setEmail(usuario.getEmail());
        usuarioCriado.setSenha(usuario.getSenha());
        usuarioCriado.setNome(usuario.getNome());

        return usuarioCriado;
    }
    //Get
    public ListarUsuarioDto buscarUsuarioPorEmail(String email){
        Usuario usuarioBuscado = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Email não encontrado"));

        return converterParaListagemDto(usuarioBuscado);
    }
    public Usuario buscarUsuarioPorId(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + " não encontrado"));
    }
    //Put
    public CadastrarEditarUsuarioDto atualizarUsuario(CadastrarEditarUsuarioDto usuario, Integer id){
       Usuario usuarioAntigo = buscarUsuarioPorId(id);

        usuarioAntigo.setEmail(usuario.getEmail());
        // String senha  = encoder.encode(usuario.getSenha());
        usuarioAntigo.setSenha(usuario.getSenha());
       usuarioAntigo.setNome(usuario.getNome());

        usuarioRepository.save(usuarioAntigo);

        return usuario;
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

    private ListarUsuarioDto converterParaListagemDto(Usuario usuario) {
        ListarUsuarioDto dto = new ListarUsuarioDto();

        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setId(usuario.getIdUsuario());

        return dto;
    }
}
