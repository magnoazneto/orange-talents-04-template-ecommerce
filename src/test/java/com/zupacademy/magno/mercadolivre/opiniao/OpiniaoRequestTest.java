package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpiniaoRequestTest {
    private OpiniaoRequest request;
    private Produto produto;
    private EntityManager manager;
    private Usuario usuarioOpinador;

    @BeforeEach
    void initSetup(){
        request = new OpiniaoRequest("Ótimo", "Descricao", 1L, 5);
        produto = mock(Produto.class);
        manager = mock(EntityManager.class);
        usuarioOpinador = mock(Usuario.class);
        when(manager.find(any(), any())).thenReturn(produto);
    }

    @Test
    @DisplayName("NAO deve criar Opiniao se usuario for NULO e produto for VALIDO")
    public void test01(){
        assertThrows(IllegalArgumentException.class, () -> {
            request.toModel(manager, null);
        });
    }

    @Test
    @DisplayName("DEVE criar Opiniao se usuario for VALIDO e produto for VALIDO")
    public void test02() {

        Opiniao opiniao = request.toModel(manager, usuarioOpinador);
        assertEquals("Ótimo", opiniao.getTitulo());
    }

    @Test
    @DisplayName("NAO deve criar Opiniao se produto for NULO e usuario for VALIDO")
    public void test03() {
        when(manager.find(any(), any())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            request.toModel(manager, usuarioOpinador);
        });
    }

}