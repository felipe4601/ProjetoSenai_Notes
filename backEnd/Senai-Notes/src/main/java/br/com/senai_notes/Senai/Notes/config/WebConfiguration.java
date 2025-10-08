package br.com.senai_notes.Senai.Notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfiguration {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**") // todos os enpoints com a raiz serão afetados
                        .allowedOrigins("http://senai-notes.work.gd","http://localhost:4200") // aqui passamos os enpoints
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "PUT","DELETE") // requisições HTTP permitidas
                        .allowCredentials(true);
            }
        };
    }
}
