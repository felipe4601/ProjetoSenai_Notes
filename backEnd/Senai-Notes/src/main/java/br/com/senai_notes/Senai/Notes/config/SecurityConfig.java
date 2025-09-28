package br.com.senai_notes.Senai.Notes.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    @Value("${api.jwt.secret}")
    private String jwtSecret;
}
