package br.com.senai_notes.Senai.Notes.dtos.usuario;

import lombok.Data;

@Data
public class ListarUsuarioDto {
    private Integer id;
    private String email;
    private String nome;
}
