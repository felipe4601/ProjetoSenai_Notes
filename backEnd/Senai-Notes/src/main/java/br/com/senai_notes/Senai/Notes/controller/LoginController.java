package br.com.senai_notes.Senai.Notes.controller;


import br.com.senai_notes.Senai.Notes.dtos.login.LoginRequest;
import br.com.senai_notes.Senai.Notes.dtos.login.LoginResponseDto;
import br.com.senai_notes.Senai.Notes.dtos.usuario.ListarUsuarioDto;

import br.com.senai_notes.Senai.Notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@Tag(name ="Login", description = "Tela de login")
public class LoginController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;


    public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.usuarioService = usuarioService;
    }


    @PostMapping()
    @ApiResponses(value = { // Usamos @ApiResponses para agrupar múltiplas respostas
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas ou usuário não encontrado",
                    content = @Content) // Resposta sem Corpo
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ListarUsuarioDto listarUsuarioDto = usuarioService.buscarUsuarioPorEmail(loginRequest.getEmail());

        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());

        Authentication auth = authenticationManager.authenticate(authToken);

        Instant now = Instant.now();
        long validade = 3600L; // 1 Hora em segundos.


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("senai_notes")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(validade)) // Quando expira.
                .subject(auth.getName())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDto(token,listarUsuarioDto));
    }
}
