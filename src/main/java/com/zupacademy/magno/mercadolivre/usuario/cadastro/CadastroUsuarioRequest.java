package com.zupacademy.magno.mercadolivre.usuario.cadastro;

import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.validations.UniqueValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CadastroUsuarioRequest {

    @NotBlank @Email @UniqueValue(targetClass = Usuario.class, fieldName = "login", message = "Esse email já está sendo utilizado por outro usuário.")
    private final String login;
    @NotBlank @Length(min = 6)
    private final String senha;

    public CadastroUsuarioRequest(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario toModel(){
        return new Usuario(this.login, new SenhaLimpa(senha));
    }
}
