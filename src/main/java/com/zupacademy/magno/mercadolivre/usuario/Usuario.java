package com.zupacademy.magno.mercadolivre.usuario;

import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Email @Column(unique = true)
    private String login;
    @NotBlank @Length(min = 6)
    private String senha;
    private LocalDateTime instanteCriacao;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();

    /**
     * apenas para uso da JPA
     */
    @Deprecated
    public Usuario(){

    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", perfis=" + perfis +
                '}';
    }
}
