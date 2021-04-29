package com.zupacademy.magno.mercadolivre.produto;

import com.zupacademy.magno.mercadolivre.caracteristica.CaracteristicaProdutoRequest;
import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {
    private Categoria categoria;
    private Usuario usuario;
    private HashSet<CaracteristicaProdutoRequest> caracteristicasPadroes;
    private Produto produtoPadrao;

    @BeforeEach
    public void iniSetup(){
        categoria = new Categoria("TECH");
        usuario = new Usuario("magno@gmail.com", new SenhaLimpa("123456"));
        caracteristicasPadroes = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("shape", "quadrado"),
                new CaracteristicaProdutoRequest("cor", "preto")));

        produtoPadrao = new Produto(
                "notebook lenovo",
                new BigDecimal("2000.0"),
                2,
                "um notebook",
                categoria,
                caracteristicasPadroes,
                usuario);

    }

    @Test
    @DisplayName("NAO deve criar Produto com menos que 3 caracteristicas")
    public void test01(){

        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("cor", "preto")));

        assertThrows(IllegalArgumentException.class, () -> {
            Produto novoProduto = new Produto(
                    "notebook lenovo",
                    new BigDecimal("2000.0"),
                    1,
                    "um notebook",
                    categoria,
                    caracteristicas,
                    usuario);
        });

    }

    @Test
    @DisplayName("DEVE criar Produto com 3 caracteristicas válidas")
    public void test02(){
        assertEquals(3, produtoPadrao.getCaracteristicas().size());
    }

    @Test
    @DisplayName("DEVE criar Produto com mais que 3 caracteristicas válidas")
    public void test03(){

        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("shape", "quadrado"),
                new CaracteristicaProdutoRequest("peso", "1.5kg"),
                new CaracteristicaProdutoRequest("cor", "preto")));

        Produto novoProduto = new Produto(
                "notebook lenovo",
                new BigDecimal("2000.0"),
                1,
                "um notebook",
                categoria,
                caracteristicas,
                usuario);

        assertEquals(4, novoProduto.getCaracteristicas().size());
    }

    @Test
    @DisplayName("NAO deve efetuar abate se a quantidade for maior que o estoque")
    public void test04(){ // in point
        boolean resultadoAbate = produtoPadrao.abaterEstoque(3);
        assertFalse(resultadoAbate);
    }

    @Test
    @DisplayName("DEVE efetuar abate se a quantidade for maior que o estoque")
    public void test05(){ // off point
        boolean resultadoAbate = produtoPadrao.abaterEstoque(1);
        assertTrue(resultadoAbate);
    }

    @Test
    @DisplayName("DEVE efetuar abate se a quantidade for igual ao estoque")
    public void test06(){
        boolean resultadoAbate = produtoPadrao.abaterEstoque(2);
        assertTrue(resultadoAbate);
    }

}