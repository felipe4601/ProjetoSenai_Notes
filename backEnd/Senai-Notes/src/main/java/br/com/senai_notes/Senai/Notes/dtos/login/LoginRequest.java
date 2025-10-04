package br.com.senai_notes.Senai.Notes.dtos.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}
