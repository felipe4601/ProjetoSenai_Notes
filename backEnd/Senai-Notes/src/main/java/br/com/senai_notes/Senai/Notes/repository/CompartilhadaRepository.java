package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.Compartilhada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompartilhadaRepository extends JpaRepository<Compartilhada, Integer> {
}
