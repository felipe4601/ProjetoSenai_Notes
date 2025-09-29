package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //Post - Criar Usuario
    @PostMapping
    @Operation(summary = "Criar Usuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    //Get - Buscar Usuario
    @GetMapping
    @Operation(summary = "Buscar Usuario")
    public ResponseEntity<?> buscarUsuarioId(Integer id) {
        Usuario user = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Buscar Usuario por email")
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioService.buscarUsuarioPorEmail(email);

    }
    //Put - Atualizar Usuario
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Usuario")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
        Usuario user = usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.ok(user);
    }
    //Delete - Deletar Usuario
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Usuario")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {
        Usuario user = usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.ok(user);
    }
}
