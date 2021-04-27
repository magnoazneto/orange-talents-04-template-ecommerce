package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProduto;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
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
    @NotNull @ManyToOne
    private Usuario usuarioCriador;

    /**
     * Construtor que deve ser usado como padrão
     * @param nome
     * @param valor
     * @param quantidade
     * @param descricao
     * @param categoria categoria cadastrada no sistema
     * @param caracteristicas caracteristicas cadastradas no sistema
     * @param usuarioCriador usuario logado que enviou a requisicao
     */
    public Produto(String nome,
                   BigDecimal valor,
                   Integer quantidade,
                   String descricao,
                   Categoria categoria,
                   Set<CaracteristicaProduto> caracteristicas,
                   Usuario usuarioCriador) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
        this.usuarioCriador = usuarioCriador;
    }

    /**
     * para uso exclusivo da JPA
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

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
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
                ", usuarioCriador=" + usuarioCriador +
                '}';
    }
}
