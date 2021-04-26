package com.zupacademy.magno.mercadolivre.usuario.cadastro;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class SenhaLimpa {

    private final String senha;

    public SenhaLimpa(String senha) {
        Assert.isTrue(senha.length() >= 6, "A senha deveria ter 6 ou mais caracteres.");
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public String hash(){
        return new BCryptPasswordEncoder().encode(senha);
    }
}
