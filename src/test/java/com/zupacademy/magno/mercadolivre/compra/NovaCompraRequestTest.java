package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NovaCompraRequestTest {

    @Test
    @DisplayName("NAO deve retornar valido para metodo de pagamento inexistente")
    public void test01(){
        NovaCompraRequest request = new NovaCompraRequest(3, "INEXISTENTE");
        assertFalse(request.metodoPagamentoValido());
    }

    @Test
    @DisplayName("DEVE retornar valido para cada metodo de pagamento cadastrado")
    public void test02(){
        Set<String> metodosPagamento = Stream.of(MetodoPagamento.values())
                .map(MetodoPagamento::name)
                .collect(Collectors.toSet());

        metodosPagamento.forEach(metodo -> {
            NovaCompraRequest request = new NovaCompraRequest(3, metodo);
            assertTrue(request.metodoPagamentoValido());
        });
    }

    @Test
    @DisplayName("NAO deve converter para model request com algum argumento nulo")
    public void test03(){
        Produto produto = Mockito.mock(Produto.class);
        Usuario comprador = Mockito.mock(Usuario.class);

        NovaCompraRequest request = new NovaCompraRequest(1, "PAYPAL");

        assertThrows(IllegalArgumentException.class, () -> {
            request.toModel(produto, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            request.toModel(null, comprador);
        });
    }

}