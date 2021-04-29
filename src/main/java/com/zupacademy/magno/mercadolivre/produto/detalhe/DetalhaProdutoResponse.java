package com.zupacademy.magno.mercadolivre.produto.detalhe;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProjecao;
import com.zupacademy.magno.mercadolivre.opiniao.OpiniaoProjecao;
import com.zupacademy.magno.mercadolivre.pergunta.PerguntaProjecao;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.ProdutoProjecao;
import com.zupacademy.magno.mercadolivre.produto.ProdutoRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalhaProdutoResponse {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Double mediaNotas;
    private Integer quantidadeNotas;
    private List<CaracteristicaProjecao> caracteristicas = new ArrayList<>();
    private List<String> imagens = new ArrayList<>();
    private List<OpiniaoProjecao> opinioes = new ArrayList<>();
    private List<PerguntaProjecao> perguntas = new ArrayList<>();


    public DetalhaProdutoResponse(@NotNull @Valid Produto produto, ProdutoRepository produtoRepository) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.descricao = produto.getDescricao();

        ProdutoProjecao detalhesProduto = produtoRepository.buscaDetalhesProduto(this.id);
        this.mediaNotas = detalhesProduto.getMediaNotas();
        this.quantidadeNotas = detalhesProduto.getQtdNotas();

        List<CaracteristicaProjecao> caracteristicasProduto = produtoRepository.buscaCaracteristicasPorProdutoId(this.id);
        caracteristicas.addAll(caracteristicasProduto);

        List<String> imagens = produtoRepository.buscaImagensPorProdutoId(this.id);
        this.imagens.addAll(imagens);

        List<OpiniaoProjecao> opinioes = produtoRepository.buscaOpinioesPorProdutoId(this.id);
        this.opinioes.addAll(opinioes);

        List<PerguntaProjecao> perguntas = produtoRepository.buscaPerguntasPorProdutoId(this.id);
        this.perguntas.addAll(perguntas);
    }

    private void testProjections(ProdutoRepository produtoRepository){

        /* recuperar a media de notas OK
         * recuperar a quantidade de notas OK
         * caracteristicas OK
         * imagens OK
         * opinioes OK
         * perguntas Ok
         */
        ProdutoProjecao detalhesProduto = produtoRepository.buscaDetalhesProduto(this.id);
        System.out.println("Nome para teste: " + detalhesProduto.getNome()); // so para testes
        System.out.println("Média de notas: " + detalhesProduto.getMediaNotas().toString());
        System.out.println("Quantidade de notas: " + detalhesProduto.getQtdNotas().toString());

        List<CaracteristicaProjecao> caracteristicasProduto = produtoRepository.buscaCaracteristicasPorProdutoId(this.id);
        caracteristicasProduto.forEach(c -> {
            System.out.println(c.getNome());
            System.out.println(c.getDescricao());
        });

        List<String> imagens = produtoRepository.buscaImagensPorProdutoId(this.id);
        imagens.forEach(System.out::println);

        List<OpiniaoProjecao> opinioes = produtoRepository.buscaOpinioesPorProdutoId(this.id);
        opinioes.forEach(o -> System.out.println(o.getTitulo()));
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

    public String getDescricao() {
        return descricao;
    }

    public Double getMediaNotas() {
        return mediaNotas;
    }

    public Integer getQuantidadeNotas() {
        return quantidadeNotas;
    }

    public List<CaracteristicaProjecao> getCaracteristicas() {
        return caracteristicas;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public List<OpiniaoProjecao> getOpinioes() {
        return opinioes;
    }

    public List<PerguntaProjecao> getPerguntas() {
        return perguntas;
    }
}
