package com.zupacademy.magno.mercadolivre.caracteristica;

import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.cadastro.SenhaLimpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CaracteristicaProdutoRequestTest {

    @Autowired
    TestEntityManager testManager;

    private Categoria categoria;
    private Usuario usuario;

    @BeforeEach
    public void iniSetup(){
        categoria = new Categoria("TECH");
        usuario = new Usuario("magno@gmail.com", new SenhaLimpa("123456"));

    }

    @Test
    @DisplayName("NAO deve receber caracteristicas com nomes iguais")
    public void test01(){
        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("cor", "preto")));


        testManager.persistAndFlush(categoria);
        testManager.persistAndFlush(usuario);

        Produto novoProduto = new Produto(
                "notebook lenovo",
                new BigDecimal("2000.0"),
                1,
                "um notebook",
                categoria,
                caracteristicas,
                usuario);

        assertThrows(PersistenceException.class, ()-> {
                testManager.persist(novoProduto);
        });
    }

    @Test
    @DisplayName("DEVE receber caracteristicas sem nomes iguais")
    public void test02(){
        Set<CaracteristicaProdutoRequest> caracteristicas = new HashSet<>(Arrays.asList(
                new CaracteristicaProdutoRequest("marca", "lenovo"),
                new CaracteristicaProdutoRequest("peso", "200.0"),
                new CaracteristicaProdutoRequest("cor", "preto")));

        testManager.persistAndFlush(categoria);
        testManager.persistAndFlush(usuario);

        Produto novoProduto = new Produto(
                "notebook lenovo",
                new BigDecimal("2000.0"),
                1,
                "um notebook",
                categoria,
                caracteristicas,
                usuario);

        Produto produtoSalvo = testManager.persistFlushFind(novoProduto);
        assertEquals(3, produtoSalvo.getCaracteristicas().size());
    }

}