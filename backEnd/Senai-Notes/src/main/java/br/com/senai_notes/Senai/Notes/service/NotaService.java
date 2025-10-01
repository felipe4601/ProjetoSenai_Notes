package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Compartilhada;
import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.CompartilhadaRepository;
import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.util.ClassUtils.ifPresent;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompartilhadaRepository compartilhadaRepository;
    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository, CompartilhadaRepository compartilhadaRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
        this.compartilhadaRepository = compartilhadaRepository;

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
        novaNota.setDataCriacao(OffsetDateTime.now());
        novaNota.setDataEdicao(OffsetDateTime.now());
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

    // UPDATE
    // Método para atualizar cadastro
    public Nota atualizarNota(Integer id, Nota novaNota){
        Nota notaExistente = buscarPorId(id);
        // Atualizando titulo
        notaExistente.setTitulo((novaNota.getTitulo()!=null && !novaNota.getTitulo().isBlank())
                ? novaNota.getTitulo() : notaExistente.getTitulo());
        // Atualizando descrição
        notaExistente.setDescricao((novaNota.getDescricao()!=null && !novaNota.getDescricao().isBlank())
                ? novaNota.getDescricao() : notaExistente.getDescricao());
        // Atualizando imagen
        notaExistente.setImagem((novaNota.getImagem()!=null && !novaNota.getImagem().isBlank())
                ? novaNota.getImagem() : notaExistente.getImagem());
        // Atualizando data de edição
        notaExistente.setDataEdicao(OffsetDateTime.now());
        // Atualizando estado da nota
        notaExistente.setEstadoNota((novaNota.getEstadoNota()!=null && !novaNota.getEstadoNota().isBlank())
            ? novaNota.getEstadoNota() : notaExistente.getEstadoNota());
        // Atualizando ehCompartilhada

        notaExistente.setEhCompartilhada(novaNota.isEhCompartilhada());

        // Atualizando usuario
        if(novaNota.getUsuario()!=null && novaNota.getUsuario().getIdUsuario()!=null){
            Integer idUsuarioAssociado = novaNota.getUsuario().getIdUsuario();
            Usuario usuarioAssociado = usuarioRepository.findById(idUsuarioAssociado)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário"));
            notaExistente.setUsuario(usuarioAssociado);

        }
        // Atualizando compartilhamento
        if(novaNota.getCompartilhada()!=null && novaNota.getCompartilhada().getIdCompartilhada()!=null){
            Integer idCompartilhamentoAssociado = novaNota.getCompartilhada().getIdCompartilhada();
            Compartilhada compartilhamentoAssociado = compartilhadaRepository.findById(idCompartilhamentoAssociado)
                    .orElseThrow(() -> new ResourceNotFoundException("Compartilhamento"));
            notaExistente.setCompartilhada(compartilhamentoAssociado);
        }
        else {
            notaExistente.setCompartilhada(null);
        }
        return notaRepository.save(notaExistente);
    }

    // DELETE
    // Método para excluir usuário
    public Nota removerNota(Integer id){
        Nota notaAssociada = buscarPorId(id);
        notaRepository.delete(notaAssociada);
        return notaAssociada;
    }

}
