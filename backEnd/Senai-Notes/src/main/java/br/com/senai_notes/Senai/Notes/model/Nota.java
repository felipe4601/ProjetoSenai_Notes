package br.com.senai_notes.Senai.Notes.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name="nota", schema = "senai_notes")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_nota")
    private Integer idNota;


    @Column(name = "titulo", nullable = false, columnDefinition = "TEXT")
    private String titulo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "imagem", nullable = false, columnDefinition = "TEXT")
    private String imagem;

    @Column(name = "data_edicao", nullable = false)
    private OffsetDateTime dataEdicao;

    @Column(name = "estado_nota", nullable = false, columnDefinition = "TEXT")
    private String estadoNota;

    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "eh_compartilha")
    private boolean ehCompartilhada;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_compartilhada", nullable = true)
    private Compartilhada compartilhada;




}
