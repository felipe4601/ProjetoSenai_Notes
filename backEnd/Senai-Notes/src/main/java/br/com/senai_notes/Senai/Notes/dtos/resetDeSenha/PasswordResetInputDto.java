package br.com.senai_notes.Senai.Notes.dtos.resetDeSenha;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class PasswordResetInputDto {
    @Email
    @NotBlank
    private String email;
}
