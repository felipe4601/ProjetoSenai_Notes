package br.com.senai_notes.Senai.Notes.dtos.login;

import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;

public record LoginResponseDto(String token, ListarUsuarioDto usuarioDto) {



}
