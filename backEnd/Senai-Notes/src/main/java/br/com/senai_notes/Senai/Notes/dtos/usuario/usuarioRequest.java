package br.com.senai_notes.Senai.Notes.dtos.usuario;


import lombok.Data;

@Data
public class usuarioRequest {
    private Integer idUsuario;
    private String email;
}
