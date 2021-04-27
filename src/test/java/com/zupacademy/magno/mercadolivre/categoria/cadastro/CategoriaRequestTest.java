package com.zupacademy.magno.mercadolivre.categoria.cadastro;

import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoriaRequestTest {

    @Autowired
    TestEntityManager testManager;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("DEVE criar categoria sem categoria mae")
    public void testaConversaoEmCateogiaSemCategoriaMae(){
        CategoriaRequest request = new CategoriaRequest("Tecnologia");

        Categoria novaCategoria = request.toModel(em);
        Categoria categoriaSalva = testManager.persistFlushFind(novaCategoria);

        assertEquals("Tecnologia", categoriaSalva.getNome());
        assertEquals(1L, categoriaSalva.getId());
        assertNull(categoriaSalva.getCategoriaMae());
    }

    @Test
    @DisplayName("DEVE associar categoria Mae quando id valido for fornecido")
    public void testaConversaoEmCateogiaComCategoriaMaeIdValido(){
        em.persist(new Categoria("Tecnologia"));
        CategoriaRequest request = new CategoriaRequest("Celulares" , 1L);

        Categoria novaCategoria = request.toModel(em);
        Categoria categoriaSalva = testManager.persistFlushFind(novaCategoria);

        assertEquals("Celulares", categoriaSalva.getNome());
        assertEquals(2L, categoriaSalva.getId());
        assertEquals("Tecnologia", categoriaSalva.getCategoriaMae().getNome());
        assertEquals(1L, categoriaSalva.getCategoriaMae().getId());
    }

    @Test
    @DisplayName("NAO deve criar categoria caso categoriId passado seja invalido")
    public void testaConversaoEmCateogiaComCategoriaMaeIdInvalido(){
        CategoriaRequest request = new CategoriaRequest("Celulares", 3L);
        assertThrows(IllegalArgumentException.class, () ->{
            Categoria novaCategoria = request.toModel(em);
        });
    }

    @Test
    @DisplayName("NAO deve criar categoria com nome já existente")
    public void testaCriacaoCategoriaNomeDuplicado(){
        Categoria novaCategoria1 = new Categoria("TECH");
        testManager.persist(novaCategoria1);
        assertThrows(PersistenceException.class, () -> {
            testManager.persist(new Categoria("TECH"));
        });
    }

}