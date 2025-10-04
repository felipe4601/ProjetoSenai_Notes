package br.com.senai_notes.Senai.Notes.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("Usuário não encontrado!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
