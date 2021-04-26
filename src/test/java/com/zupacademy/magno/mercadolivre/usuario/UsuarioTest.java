package com.zupacademy.magno.mercadolivre.usuario;

import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private String usuarioLogin;
    private SenhaLimpa senhaLimpa;

    @BeforeEach
    public void setUp(){
        usuarioLogin = "magno@gmail.com";
        senhaLimpa = new SenhaLimpa("123456");
    }

    @Test
    @DisplayName("DEVE criar usuario com login e senha validos")
    public void testUserCreation(){
        Usuario novoUsuario = new Usuario(usuarioLogin, senhaLimpa);
        assertEquals(usuarioLogin, novoUsuario.getLogin());
    }

    @Test
    @DisplayName("NAO deve aceitar senha com menos de 6 caracteres")
    public void testPassword(){
        assertThrows(IllegalArgumentException.class, () -> {
            Usuario novoUsuario = new Usuario(usuarioLogin, new SenhaLimpa("12345"));
        });
    }

    @Test
    @DisplayName("NAO deve aceitar login em branco")
    public void testLogin(){
        assertThrows(IllegalArgumentException.class, () -> {
            Usuario novoUsuario = new Usuario("", senhaLimpa);
        });
    }

    @Test
    @DisplayName("DEVE retornar senha criptografada do usuario")
    public void testEncoding(){
        Usuario novoUsuario = new Usuario(usuarioLogin, senhaLimpa);
        assertNotEquals(senhaLimpa.getSenha(), novoUsuario.getSenha());
    }

}