package br.com.senai_notes.Senai.Notes.service;


import br.com.senai_notes.Senai.Notes.dtos.resetDeSenha.PasswordTokenPublicDataDto;
import br.com.senai_notes.Senai.Notes.exception.ResourceNotFoundException;
import br.com.senai_notes.Senai.Notes.model.Usuario;
import br.com.senai_notes.Senai.Notes.repository.UsuarioRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class UsuarioSenhaService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioSenhaService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para gerar token
    @SneakyThrows
    public String gerarToken(Usuario usuario){
        KeyBasedPersistenceTokenService tokenService = getInstaceFor(usuario);

        Token token = tokenService.allocateToken(usuario.getEmail());

        return token.getKey();
    }

    private KeyBasedPersistenceTokenService getInstaceFor(Usuario usuario) throws Exception {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        tokenService.setServerSecret(usuario.getPassword());
        tokenService.setServerInteger(27);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        return tokenService;
    }

    // Método de alteração de senha
    @SneakyThrows
    public void alterarSenha(String novaSenha, String token ){
        PasswordTokenPublicDataDto publicData = readPublicData(token);
        if(isExpired(publicData)){
            throw new RuntimeException("Token expirado");
        }
        Usuario usuario = usuarioRepository.findByEmail(publicData.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        KeyBasedPersistenceTokenService tokenService = this.getInstaceFor(usuario);
        tokenService.verifyToken(token);
        usuario.setSenha(this.passwordEncoder.encode(novaSenha));
    }

    private boolean isExpired(PasswordTokenPublicDataDto publicData) {
        Instant createdAt =  new Date(publicData.getCreatedAtTimestamp()).toInstant();
        Instant now = new Date().toInstant();
        return createdAt.plus(Duration.ofMinutes(5)).isBefore(now);
        // se a data de expiração do token mais cinco minutos for depois de hoje
        // este token já está expirado
    }

    private PasswordTokenPublicDataDto readPublicData(String rawToken) {
        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];
        return new PasswordTokenPublicDataDto(email, timestamp);

    }
}
