package br.com.senai_notes.Senai.Notes.dtos.anotacao;

import lombok.Data;

import java.util.List;

@Data
public class CadastrarEditarAnotacaoDto {
    private String titulo;
    private String descricao;
    private String imagem;
    private Integer usuarioId;
    private List<String> tags;
}
