package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByUsuarioEmailIgnoreCase(String email);
    List<Tag> findAllByNomeInAndUsuarioIdUsuario(List<String> nome, Integer id);

}
