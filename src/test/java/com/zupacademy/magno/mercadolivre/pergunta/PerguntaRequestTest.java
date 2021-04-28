package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PerguntaRequestTest {

    private PerguntaRequest request;
    private Usuario usuarioCriador;
    private Produto produtoAlvo;

    @BeforeEach
    public void initSetup(){
        request = new PerguntaRequest("Qual a RAM?", 1L);
        usuarioCriador = mock(Usuario.class);
        produtoAlvo = mock(Produto.class);
    }


    @Test
    @DisplayName("DEVE criar Pergunta com usuario VALIDO e produto VALIDO")
    public void test01(){
        Pergunta pergunta = request.toModel(usuarioCriador, produtoAlvo);
        assertEquals("Qual a RAM?", pergunta.getTitulo());
    }

    @Test
    @DisplayName("NAO deve criar Pergunta com usuario NULO e produto VALIDO")
    public void test02(){
        assertThrows(IllegalArgumentException.class, () -> {
            Pergunta pergunta = request.toModel(null, produtoAlvo);
        });
    }

    @Test
    @DisplayName("NAO deve criar Pergunta com usuario VALIDO e produto NULO")
    public void test03(){
        assertThrows(IllegalArgumentException.class, () -> {
            Pergunta pergunta = request.toModel(usuarioCriador, null);
        });
    }

}