package br.com.senai_notes.Senai.Notes.controller;

import br.com.senai_notes.Senai.Notes.dtos.login.LoginRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;


    public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }


    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());

        Authentication auth = authenticationManager.authenticate(authToken);

        Instant now = Instant.now();
        long validade = 3600L; // 1 Hora em segundos.


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("urbanswift-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(validade)) // Quando expira.
                .subject(auth.getName())
                .claim("roles", auth.getAuthorities())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return ResponseEntity.ok(token);
    }
}
