package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProduto;
import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProdutoRequest;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull @Positive
    private BigDecimal valor;
    @NotNull @PositiveOrZero
    private Integer quantidade;
    @NotBlank @Length(max = 1000)
    private String descricao;
    @NotNull @ExistsValue(targetClass = Categoria.class, fieldName = "id", message = "Categoria não existe para o ID informado.")
    private Long categoriaId;
    @NotNull @Size(min = 3)
    private Set<CaracteristicaProdutoRequest> caracteristicas;

    public ProdutoRequest(String nome,
                          BigDecimal valor,
                          Integer quantidade,
                          String descricao,
                          Long categoriaId,
                          Set<CaracteristicaProdutoRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.caracteristicas = caracteristicas;
    }

    public Produto toModel(EntityManager manager){
        Categoria categoria = manager.find(Categoria.class, categoriaId);
        Assert.notNull(categoria, "Categoria não deveria ser nula");

        Set<CaracteristicaProduto> caracteristicasSalvas = salvarCaracteristicas(manager);

        return new Produto(this.nome,
                this.valor,
                this.quantidade,
                this.descricao,
                categoria,
                caracteristicasSalvas
                );
    }

    private Set<CaracteristicaProduto> salvarCaracteristicas(EntityManager manager){
        Set<CaracteristicaProduto> caracteristicasSalvas = new HashSet<>();

        caracteristicas.forEach(c -> {
            CaracteristicaProduto novaCaracteristica = c.toModel();
            manager.persist(novaCaracteristica);
            caracteristicasSalvas.add(novaCaracteristica);
        });

        return caracteristicasSalvas;

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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public Set<CaracteristicaProdutoRequest> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return "ProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoriaId=" + categoriaId +
                ", caracteristicas=" + caracteristicas +
                '}';
    }
}
