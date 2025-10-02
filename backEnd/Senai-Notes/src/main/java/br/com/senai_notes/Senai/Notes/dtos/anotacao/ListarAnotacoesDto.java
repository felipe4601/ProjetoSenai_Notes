package br.com.senai_notes.Senai.Notes.dtos.anotacao;

import br.com.senai_notes.Senai.Notes.dtos.tag.ListarTagDto;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ListarAnotacoesDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private String imagem;
    private String estadoNota;
    private OffsetDateTime dataEdicao;
    private List<ListarTagDto> tag;

}
