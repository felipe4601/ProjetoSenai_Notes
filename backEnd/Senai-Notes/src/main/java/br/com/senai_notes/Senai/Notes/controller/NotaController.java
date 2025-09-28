package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.model.Nota;
import br.com.senai_notes.Senai.Notes.service.NotaService;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    // CRUD
    // Método para cadastrar nota
    @PostMapping
    public ResponseEntity<?> cadastrarNota(@RequestBody Nota novaNota){
        notaService.cadastrarNota(novaNota);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }
    // READ
    // Método para mostrar nota
    @GetMapping("/{email}")
    public  ResponseEntity<?> listarNotasUsuario(@PathVariable String email){
        List<Nota> notas = notaService.listarPorUsuario(email);
        return ResponseEntity.ok(notas);
    }

    // UPDATE
    //
    // Método para atualizar nota
    @PutMapping("/{id}")
    public ResponseEntity<?> editarNota(@PathVariable Integer id, @RequestBody Nota notaAtualizada){
        Nota notaExistente = notaService.atualizarNota(id, notaAtualizada);
        return ResponseEntity.ok(notaExistente);
    }

    // DELETE
    // Método para deletar nota
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerNota(@PathVariable Integer id){
        notaService.removerNota(id);
        return ResponseEntity.noContent().build();
    }

}
