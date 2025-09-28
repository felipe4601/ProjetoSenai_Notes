package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

}
