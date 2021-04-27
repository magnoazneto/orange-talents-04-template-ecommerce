package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProduto;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotNull @PositiveOrZero
    private Integer quantidade;
    @NotBlank @Length(max = 1000)
    private String descricao;
    @NotNull @ManyToOne
    private Categoria categoria;
    @NotNull @Size(min = 3) @OneToMany
    private Set<CaracteristicaProduto> caracteristicas;

    /**
     * construtor padrao
     * @param nome
     * @param valor
     * @param quantidade
     * @param descricao
     * @param categoria categoria cadastrada no sistema
     * @param caracteristicas caracteristicas cadastradas no sistema
     */
    public Produto(String nome,
                   BigDecimal valor,
                   Integer quantidade,
                   String descricao,
                   Categoria categoria,
                   Set<CaracteristicaProduto> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
    }

    /**
     * para uso da JPA
     */
    @Deprecated
    public Produto(){

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Set<CaracteristicaProduto> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", caracteristicas=" + caracteristicas +
                '}';
    }
}
