package com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String login;
    @NotBlank @Length(min = 6)
    private String senha;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public UsernamePasswordAuthenticationToken gerarDadosParaToken() {
        return new UsernamePasswordAuthenticationToken(this.login, this.senha);
    }
}
