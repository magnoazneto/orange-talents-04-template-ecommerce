package com.zupacademy.magno.mercadolivre.caracteristica;

import javax.validation.constraints.NotBlank;

public class CaracteristicaProdutoRequest {
    @NotBlank
    private String nome;
    private String descricao;

    public CaracteristicaProdutoRequest(@NotBlank String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }


    public CaracteristicaProduto toModel(){
        return new CaracteristicaProduto(this.nome, this.descricao);
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
