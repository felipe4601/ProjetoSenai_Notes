package br.com.senai_notes.Senai.Notes.repository;


import br.com.senai_notes.Senai.Notes.model.TagNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TagNotaRepository  extends JpaRepository <TagNota, Integer> {
      List<TagNota> findAllByNotaId(Integer id);
      }


