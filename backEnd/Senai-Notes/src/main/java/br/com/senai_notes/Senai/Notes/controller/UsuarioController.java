package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.dtos.usuario.CadastrarEditarUsuarioDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario", description = "Metodos para controle de usuario")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //Post - Criar Usuario
    @PostMapping
    @Operation(summary = "Criar Usuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody CadastrarEditarUsuarioDto usuario) {
        Usuario user = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    //Get - Buscar Usuario
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuario")
    public ResponseEntity<?> buscarUsuarioId(Integer id) {
        Usuario user = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{email}")
    @PreAuthorize("#email == authentication.getName()")
    @Operation(summary = "Buscar Usuario por email")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email) {
        ListarUsuarioDto usuarioBuscado = usuarioService.buscarUsuarioPorEmail(email);

        return ResponseEntity.ok(usuarioBuscado);

    }
    //Put - Atualizar Usuario
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Usuario")
    public ResponseEntity<?> atualizarUsuario(@RequestBody CadastrarEditarUsuarioDto usuario, @PathVariable Integer id) {
        CadastrarEditarUsuarioDto user = usuarioService.atualizarUsuario(usuario, id);
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
