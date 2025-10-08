package br.com.senai_notes.Senai.Notes.repository;

import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByUsuarioEmail(String email);
    @Query("SELECT DISTINCT n FROM Nota n " +
           "LEFT JOIN FETCH n.usuario u " +
            "LEFT JOIN FETCH n.tagAnotacao ta " +
            "LEFT JOIN FETCH ta.tag t " +
            " WHERE LOWER(u.email) = LOWER(:emailDoUsuario)")
    List<Nota> findByUsuarioEmailCompleto(@Param("emailDoUsuario") String email);
}
