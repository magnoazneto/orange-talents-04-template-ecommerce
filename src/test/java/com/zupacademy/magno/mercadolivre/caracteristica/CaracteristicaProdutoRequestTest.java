package com.zupacademy.magno.mercadolivre.caracteristica;

import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.cadastro.ProdutoRequest;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CaracteristicaProdutoRequestTest {


    private Categoria categoria;
    private Usuario usuario;

    @BeforeEach
    public void iniSetup(){
        categoria = Mockito.mock(Categoria.class);
        usuario = Mockito.mock(Usuario.class);

    }

    @Test
    @DisplayName("DEVE detectar caracteristicas com nomes iguais")
    public void test01(){
        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("cor", "preto")));


        ProdutoRequest novoProduto = new ProdutoRequest( "notebook lenovo",
                new BigDecimal("2000.0"),
                1,
                "um notebook",
                1L,
                caracteristicas);

        Set<String> nomesIguais = novoProduto.buscarCategoriasComNomeIgual();
        assertEquals(1, nomesIguais.size());

    }

    @Test
    @DisplayName("NAO DEVE detectar caracteristicas com nomes iguais quando nao houver")
    public void test02(){
        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("peso", "2kg"),
                new CaracteristicaProdutoRequest("cor", "preto")));


        ProdutoRequest novoProduto = new ProdutoRequest( "notebook lenovo",
                new BigDecimal("2000.0"),
                1,
                "um notebook",
                1L,
                caracteristicas);

        Set<String> nomesIguais = novoProduto.buscarCategoriasComNomeIgual();
        assertEquals(0, nomesIguais.size());

    }
}