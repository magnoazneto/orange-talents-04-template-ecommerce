package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NovaCompraRequest {

    @NotNull @Positive
    private Integer quantidade;
    @NotNull
    private String metodoPagamento;

    @Deprecated
    public NovaCompraRequest() {
    }

    public NovaCompraRequest(Integer quantidade, String metodoPagamento) {
        this.quantidade = quantidade;
        this.metodoPagamento = metodoPagamento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public boolean metodoPagamentoValido(){
        Set<String> metodosPagamento = Stream.of(MetodoPagamento.values())
                .map(MetodoPagamento::name)
                .collect(Collectors.toSet());

        return metodosPagamento.contains(this.getMetodoPagamento());
    }

    public Compra toModel(Produto produto, Usuario comprador) {
        MetodoPagamento metodoPagamento = MetodoPagamento.valueOf(this.metodoPagamento);

        Assert.notNull(metodoPagamento, "Método de Pagamento não deveria ser nulo.");
        Assert.notNull(produto, "Produto não deveria ser nulo.");
        Assert.notNull(comprador, "Usuário comprador não deveria ser nulo.");

        return new Compra(
                produto,
                comprador,
                metodoPagamento,
                produto.getValor(),
                this.quantidade);
    }
}
