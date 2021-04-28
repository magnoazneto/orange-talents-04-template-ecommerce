package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import io.jsonwebtoken.lang.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PerguntaRequest {

    @NotBlank
    private String titulo;


    @Deprecated
    public PerguntaRequest() {
    }

    public PerguntaRequest(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }


    @Override
    public String toString() {
        return "PerguntaRequest{" +
                "titulo='" + titulo + '\'' +
                '}';
    }

    public Pergunta toModel(Usuario criadorPergunta, Produto produtoAlvo) {
        Assert.notNull(produtoAlvo, "Produto não deveria ser nulo.");
        Assert.notNull(criadorPergunta, "Criador da pergunta não deveria ser nulo.");

        return new Pergunta(this.titulo,
                produtoAlvo,
                criadorPergunta);
    }
}
