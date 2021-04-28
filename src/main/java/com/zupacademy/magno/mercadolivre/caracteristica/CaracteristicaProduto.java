package com.zupacademy.magno.mercadolivre.caracteristica;

import com.zupacademy.magno.mercadolivre.produto.Produto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CaracteristicaProduto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(unique = true)
    private String nome;
    private String descricao;
    @ManyToOne
    private @NotNull @Valid Produto produto;

    /**
     * para uso do hibernate
     */
    @Deprecated
    public CaracteristicaProduto(){

    }

    public CaracteristicaProduto(@NotBlank String nome,
                                 String descricao,
                                 @NotNull @Valid Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "CaracteristicaProduto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
