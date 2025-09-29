package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.TagNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

interface TagNotaRepository extends JpaRepository<TagNota, Integer> {
}
