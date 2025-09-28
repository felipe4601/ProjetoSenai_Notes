package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.service.NotaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notas")
public class NotaController {
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

  //  @GetMapping("/{email}")
}
