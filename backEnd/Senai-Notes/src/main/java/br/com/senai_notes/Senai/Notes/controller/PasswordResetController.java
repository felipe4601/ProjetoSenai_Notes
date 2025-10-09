package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.dtos.resetDeSenha.PasswordResetInputDto;
import br.com.senai_notes.Senai.Notes.dtos.resetDeSenha.PasswordResetWithTokenInput;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import br.com.senai_notes.Senai.Notes.service.UsuarioSenhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PasswordResetController {

    private final UsuarioSenhaService usuarioSenhaService;
    private final UsuarioRepository usuarioRepository;


    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody @Valid PasswordResetInputDto input){
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(input.getEmail());
        optionalUsuario.ifPresent(usuario -> {
            String token = usuarioSenhaService.gerarToken(usuario);
            // email
            System.out.println(token);
        });
    }
    @PostMapping("/change-password")
    public void changePassword(PasswordResetWithTokenInput input){
        try {
            usuarioSenhaService.alterarSenha(input.getPassword(), input.getToken());
        } catch (Exception e) {
            log.error("Erro ao alterar a senha usando token", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
