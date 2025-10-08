package br.com.senai_notes.Senai.Notes.dtos.tag;


import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;
import br.com.senai_notes.Senai.Notes.model.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ListarTagDto {
    private Integer id;
    private String nome;

}
