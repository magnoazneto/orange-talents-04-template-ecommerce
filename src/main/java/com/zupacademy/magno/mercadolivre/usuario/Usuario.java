package com.zupacademy.magno.mercadolivre.usuario;

import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Email @Column(unique = true)
    private final String login;
    @NotBlank @Length(min = 6)
    private final String senha;
    private final LocalDateTime instanteCriacao;

    /**
     *
     * @param login login do usuario em formato de email
     * @param senhaLimpa senha limpa sem criptografia
     */
    public Usuario(@NotBlank @Email String login,
                   @NotNull SenhaLimpa senhaLimpa) {
        Assert.hasLength(login, "Login não pode ser em branco.");
        Assert.isTrue(senhaLimpa.getSenha().length() >= 6, "A senha deveria ter 6 ou mais caracteres.");

        this.login = login;
        this.senha = senhaLimpa.hash();
        instanteCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
