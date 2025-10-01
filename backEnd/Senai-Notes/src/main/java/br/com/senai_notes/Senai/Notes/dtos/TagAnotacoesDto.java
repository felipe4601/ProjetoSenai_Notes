package br.com.senai_notes.Senai.Notes.dtos;

import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Tag;
import lombok.Data;

import java.util.List;

@Data
public class TagAnotacoesDto {
    private List<Integer> idTag;
    private Integer idNota;
}
