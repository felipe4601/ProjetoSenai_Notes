package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Compartilhada;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.NotaRepository;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import java.time.OffsetDateTime;



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
        notaExistente.setTitulo((novaNota.getTitulo()!=null && novaNota.getTitulo().isBlank())
                ? notaExistente.getTitulo() : novaNota.getTitulo());
        // Atualizando descrição
        notaExistente.setDescricao((novaNota.getDescricao()!=null && novaNota.getDescricao().isBlank())
                ? notaExistente.getDescricao() : novaNota.getDescricao());
        // Atualizando imagen
        notaExistente.setImagem((novaNota.getImagem()!=null && novaNota.getImagem().isBlank())
                ? notaExistente.getImagem() : novaNota.getImagem());
        // Atualizando data de edição
        notaExistente.setDataEdicao(OffsetDateTime.now());
        // Atualizando estado da nota
        notaExistente.setEstadoNota((novaNota.getEstadoNota()!=null && novaNota.getEstadoNota().isBlank())
            ? notaExistente.getEstadoNota() : novaNota.getEstadoNota());
        // Atualizando ehCompartilhada
        notaExistente.setEhCompartilhada(novaNota.isEhCompartilhada());
        novaNota.setDataCriacao(notaExistente.getDataCriacao());
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
        }
       return notaRepository.save(novaNota);
    }

    // Método para buscar nota por usuário
    public Nota buscarPorId(Integer id){
        return notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota"));
    }


    // DELETE
    // Método para excluir usuário
    public Nota removerNota(Integer id){
        Nota notaAssociada = buscarPorId(id);
        notaRepository.delete(notaAssociada);
        return notaAssociada;
    }

}
