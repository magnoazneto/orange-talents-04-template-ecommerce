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
    @NotNull @ExistsValue(targetClass = Produto.class, fieldName = "id", message = "Produto não existente para esse ID.")
    private Long produtoId;

    public PerguntaRequest(@NotBlank String titulo, @NotNull Long produtoId) {
        this.titulo = titulo;
        this.produtoId = produtoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    @Override
    public String toString() {
        return "PerguntaRequest{" +
                "titulo='" + titulo + '\'' +
                ", produtoId=" + produtoId +
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
