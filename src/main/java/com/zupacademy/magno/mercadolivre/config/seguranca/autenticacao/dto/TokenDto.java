package com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.dto;

public class TokenDto {
    private final String token;
    private final String tipo;

    public TokenDto(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
