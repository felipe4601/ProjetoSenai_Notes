package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.model.Compartilhada;
import br.com.senai_notes.Senai.Notes.service.CompartilhadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compartilhada")

@Tag(name = "Compartilhada", description = "Essa função será usada para compartilhar notas entre usuarios")
public class CompartilhadaController {
    private final CompartilhadaService compartilhadaService;

    public CompartilhadaController(CompartilhadaService compartilhadaService) {
        this.compartilhadaService = compartilhadaService;
    }

    //Criar - Post
    @PostMapping
    @Operation(summary = "Criar compartilhamento", description = "Cria compartilhamento")
    public ResponseEntity<Compartilhada>  criarCompartilhamento(@RequestBody Compartilhada compartilhada){
        Compartilhada comp = compartilhadaService.criarCompartilhada(compartilhada);
        return ResponseEntity.status(HttpStatus.CREATED).body(comp);
    }
    //Ler - Get
    @GetMapping("/{id}")
    @Operation(summary = "Ler compartilhada por id", description = "ler compartilhada por id")
    public ResponseEntity<?> lerCompartilhadaPorId(@PathVariable("id") Integer id){
        Compartilhada compartilhada = compartilhadaService.buscarCompartilhadaPorId(id);
        if(compartilhada == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
        return ResponseEntity.ok(compartilhada);
    }
    //Atualizar - Put
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Compartilhamento")
    public ResponseEntity<?> editarCompartilhada(@PathVariable("id") Integer id, @RequestBody Compartilhada compartilhada){
        Compartilhada comp =  compartilhadaService.buscarCompartilhadaPorId(id);
        if(comp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
        return ResponseEntity.ok(compartilhada);
    }
    //Deletar - Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Compartilhamento")
    public ResponseEntity<?> deletarCompartilhada(@PathVariable("id") Integer id){
        Compartilhada comp = compartilhadaService.buscarCompartilhadaPorId(id);
        if(comp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
        return ResponseEntity.ok(comp);
    }
}
