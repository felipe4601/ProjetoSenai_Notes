package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
}
