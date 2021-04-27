package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProduto;
import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProdutoRequest;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    public Produto toModel(EntityManager manager, Usuario usuario){
        Categoria categoria = manager.find(Categoria.class, categoriaId);
        Assert.notNull(categoria, "Categoria não deveria ser nula");

        Usuario usuarioLogado = manager.find(Usuario.class, usuario.getId());
        Assert.notNull(usuarioLogado, "Usuário logado não foi encontrado");

        return new Produto(this.nome,
                this.valor,
                this.quantidade,
                this.descricao,
                categoria,
                caracteristicas,
                usuarioLogado
                );
    }

    public Set<String> buscarCategoriasComNomeIgual(){
        Set<String> nomesIguais = new HashSet<>();
        Set<String> resultados = new HashSet<>();
        for (CaracteristicaProdutoRequest c : caracteristicas) {
            String nome = c.getNome();
            if (!nomesIguais.add(nome)) {
                resultados.add(nome);
            }
        }
        return resultados;
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
