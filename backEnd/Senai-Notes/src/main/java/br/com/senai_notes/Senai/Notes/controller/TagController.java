package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.dtos.tag.CadastrarEditarTagDto;
import br.com.senai_notes.Senai.Notes.dtos.tag.ListarTagDto;
import br.com.senai_notes.Senai.Notes.model.Tag;
import br.com.senai_notes.Senai.Notes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Endpoints para gestão de tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    // CRUD
    // CREATE
    // Método para cadastrar usando dto
    @PostMapping
    @Operation(summary = "Cadastrar uma nova tag", description = "Cria uma nova tag e armazena no banco de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tag cadastrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Tag> cadastrarTag(@RequestBody CadastrarEditarTagDto tagDto){
        Tag novaTag = tagService.cadastrarTagDto(tagDto);
        return ResponseEntity.ok(novaTag);
    }
    // READ
    // Método para listar todas as tags usando dto
    @GetMapping
    @Operation(summary = "Listar todas as tags", description = "Retorna todas as tags existentes.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    public ResponseEntity<List<ListarTagDto>> listarTodasTags(){
        List<ListarTagDto> tags = tagService.listarTodasTags();
        return ResponseEntity.ok(tags);
    }
    // Método para listar tags pelo email do usuário associado
    @GetMapping("/usuario/{email}")
    @Operation(summary = "Lista todas as tags de um usuário", description = "Retorna todas as tags de um usuário pelo email dele")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    public ResponseEntity<List<ListarTagDto>> listarTagsPorEmail(@PathVariable String email){
        List<ListarTagDto> tags = tagService.listarTagsPorEmail(email);
        return ResponseEntity.ok(tags);
    }
    // UPDATE
    // Método para atualizar tag
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tag existente", description = "Atualizado os dados de uma tag, a buscando pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag atualizada"),
            @ApiResponse(responseCode = "404", description = "Tag ou usuário não encontrado")
    })
    public ResponseEntity<Tag> atualizarTag(@PathVariable Integer id, @RequestBody CadastrarEditarTagDto dto){
        Tag tagAtualizada = tagService.atualizarTag(id, dto);
        return ResponseEntity.ok(tagAtualizada);
    }

    // DELETE
    // Método para deletar tag
    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga uma tag", description = "Apaga uma tag do banco de dados buscando pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public ResponseEntity<Void> deletarTag(@PathVariable Integer id){
        Tag tagDeletada = tagService.deletarTag(id);
        return ResponseEntity.noContent().build();
    }

}
