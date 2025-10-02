package br.com.senai_notes.Senai.Notes.dtos.usuario;


import lombok.Data;

@Data
public class CadastrarEditarUsuarioDto {
    private String email;
    private String senha;
}
