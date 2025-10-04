package br.com.senai_notes.Senai.Notes.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity

@Table(name = "usuario" )
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Email(message="Email inválido")
    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message="O campo não pode estar em branco")
    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message="O campo não pode estar em branco")
    @Column(name = "senha", nullable = false, columnDefinition = "TEXT")
    private String senha;

    @Column(name = "nome", nullable = true, columnDefinition = "TEXT")
    private String nome;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    @Override
    @JsonIgnore
    public String getPassword() {

        return this.senha;
    }

    @Override
    @JsonIgnore
    public String getUsername() {

        return this.email;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
