package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProduto;
import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProdutoRequest;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.opiniao.Opiniao;
import com.zupacademy.magno.mercadolivre.pergunta.Pergunta;
import com.zupacademy.magno.mercadolivre.produto.cadastro.imagens.ImagemProduto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @NotNull @Size(min = 3) @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
    @NotNull @ManyToOne
    private Usuario usuarioCriador;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();
    @OneToMany(mappedBy = "produto")
    private Set<Pergunta> perguntas = new HashSet<>();
    @OneToMany(mappedBy = "produto")
    private List<Opiniao> opinioes = new ArrayList<>();

    /**
     * Construtor que deve ser usado como padrão
     * @param nome
     * @param valor
     * @param quantidade
     * @param descricao
     * @param categoria categoria cadastrada no sistema
     * @param caracteristicas caracteristicas a serem cadastradas no sistema com o produto
     * @param usuarioCriador usuario logado que enviou a requisicao
     */
    public Produto(String nome,
                   BigDecimal valor,
                   Integer quantidade,
                   String descricao,
                   Categoria categoria,
                   @NotNull @Size(min = 3) Set<CaracteristicaProdutoRequest> caracteristicas,
                   Usuario usuarioCriador) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuarioCriador = usuarioCriador;
        this.caracteristicas.addAll(caracteristicas.stream().map(c -> c.toModel(this)).collect(Collectors.toSet()));

        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no minimo 3 caracteristicas.");
    }

    /**
     * para uso exclusivo da JPA
     */
    @Deprecated
    public Produto(){

    }

    public void associaLinks(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream().map(l -> new ImagemProduto(this, l)).collect(Collectors.toSet());
        this.imagens.addAll(imagens);
    }

    public <T> Set<T> mapCaracteristicas(Function<CaracteristicaProduto, T> funcaoMapeadora){
        return this.caracteristicas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T> Set<T> mapImagens(Function<ImagemProduto, T> funcaoMapeadora){
        return this.imagens.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T> Set<T> mapPerguntas(Function<Pergunta, T> funcaoMapeadora){
        return this.perguntas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T> List<T> mapOpinioes(Function<Opiniao, T> funcaoMapeadora){
        return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toList());
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
                ", imagens=" + imagens +
                '}';
    }
}
