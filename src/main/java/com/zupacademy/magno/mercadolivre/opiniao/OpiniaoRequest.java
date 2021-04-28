package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoRequest {

    @NotBlank
    private String titulo;
    @NotBlank @Length(max = 500)
    private String descricao;
    @NotNull @ExistsValue(targetClass = Produto.class, fieldName = "id", message = "Produto não encontrado para esse id")
    private Long produtoId;
    @NotNull @Range(min = 1, max = 5)
    private Integer nota;

    public OpiniaoRequest(String titulo, String descricao, Long produtoId, Integer nota) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.produtoId = produtoId;
        this.nota = nota;
    }

    public Opiniao toModel(EntityManager manager, Usuario usuarioOpinador) {
        Produto produto = manager.find(Produto.class, produtoId);
        Assert.notNull(produto, "o Produto não deveria ser nulo.");

        return new Opiniao(this.titulo,
                this.descricao,
                produto,
                this.nota,
                usuarioOpinador
                );
    }

    @Override
    public String toString() {
        return "OpiniaoRequest{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", produtoId=" + produtoId +
                ", nota=" + nota +
                '}';
    }
}
