package br.com.senai_notes.Senai.Notes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class TagNota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag_nota")
    private Long idTagNota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota")
    private Nota idNota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tag")
    private Tag idTag;
}
