package com.zupacademy.magno.mercadolivre.caracteristica;

import com.zupacademy.magno.mercadolivre.produto.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CaracteristicaProdutoRequest {
    @NotBlank
    private String nome;
    private String descricao;

    public CaracteristicaProdutoRequest(@NotBlank String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }


    public CaracteristicaProduto toModel(@NotNull @Valid Produto produto){
        return new CaracteristicaProduto(this.nome, this.descricao, produto);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }


    @Override
    public String toString() {
        return "CaracteristicaProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
