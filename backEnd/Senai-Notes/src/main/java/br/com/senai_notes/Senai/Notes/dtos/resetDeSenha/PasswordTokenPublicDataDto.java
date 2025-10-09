package br.com.senai_notes.Senai.Notes.dtos.resetDeSenha;


import lombok.Data;

@Data
public class PasswordTokenPublicDataDto {
    private final String email;
    private final Long createdAtTimestamp;

}
