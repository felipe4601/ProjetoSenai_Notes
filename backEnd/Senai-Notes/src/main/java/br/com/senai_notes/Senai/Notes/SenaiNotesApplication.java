package br.com.senai_notes.Senai.Notes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API do Projeto SENAI Notes",
        version = "1.0",
        description = "API responsável pelo gerenciamento de usuários, tags, anotações e processo de Login do Projeto SENAI Notes"
))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SenaiNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenaiNotesApplication.class, args);
	}

}
