package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.email.ResetarSenhaDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.CadastrarEditarUsuarioDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    //Post
    public Usuario criarUsuario(CadastrarEditarUsuarioDto usuario){
        Usuario usuarioCriado = new Usuario();

        String senha = passwordEncoder.encode(usuario.getSenha());

        usuarioCriado.setSenha(senha);
        usuarioCriado.setEmail(usuario.getEmail());
        usuarioCriado.setNome(usuario.getNome());

        usuarioRepository.save(usuarioCriado);
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
        String senha  = passwordEncoder.encode(usuario.getSenha());
        usuarioAntigo.setSenha(senha);
       usuarioAntigo.setNome(usuario.getNome());

        usuarioRepository.save(usuarioAntigo);

        return usuario;
    }
    //Delete.
    public Usuario deletarUsuarioPorId(Integer id){
        Usuario user = buscarUsuarioPorId(id);
        if (user == null){
            return null;
        }
        usuarioRepository.deleteById(id);
        return user;
    }
    //converter
    private ListarUsuarioDto converterParaListagemDto(Usuario usuario) {
        ListarUsuarioDto dto = new ListarUsuarioDto();

        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setId(usuario.getIdUsuario());

        return dto;
    }

    // Método para recuperar
    public void recuperarSenha(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            String novaSenha = RandomStringUtils.randomAlphanumeric(10);
            String senhaCodificada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCodificada);
            usuarioRepository.save(usuario);
            emailService.enviarEmail(usuario.getEmail(), novaSenha);
        });
    }


}
