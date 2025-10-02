package br.com.senai_notes.Senai.Notes.anotacao;

import lombok.Data;

import java.util.List;

@Data
public class CadastrarAnotacaoDto {
    private List<Integer> idTag;
    private Integer idNota;
}
