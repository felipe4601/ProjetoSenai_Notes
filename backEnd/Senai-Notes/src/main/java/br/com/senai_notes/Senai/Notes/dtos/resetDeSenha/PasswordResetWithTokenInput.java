package br.com.senai_notes.Senai.Notes.dtos.resetDeSenha;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetWithTokenInput {
    @NotBlank
    private String password;

    @NotBlank
    private String token;
}
