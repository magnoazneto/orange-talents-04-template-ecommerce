package com.zupacademy.magno.mercadolivre.compra.gateways;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetodoPagamentoTest {

    @Test
    @DisplayName("PAYPAL NAO deve aceitar status de tentativa nao reconhecido")
    public void test01(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAYPAL;
        boolean resultado = metodoPagamento.contemStatus("SUCESSO");
        assertFalse(resultado);
    }

    @Test
    @DisplayName("PAYPAL deve aceitar status de tentativa 0")
    public void test02(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAYPAL;
        boolean resultado = metodoPagamento.contemStatus("0");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("PAYPAL deve aceitar status de tentativa 1")
    public void test03(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAYPAL;
        boolean resultado = metodoPagamento.contemStatus("1");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("PAGSEGURO NAO deve aceitar status de tentativa nao reconhecido")
    public void test04(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAGSEGURO;
        boolean resultado = metodoPagamento.contemStatus("0");
        assertFalse(resultado);
    }

    @Test
    @DisplayName("PAGSEGURO deve aceitar status de tentativa FALHA")
    public void test05(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAGSEGURO;
        boolean resultado = metodoPagamento.contemStatus("FALHA");
        assertTrue(resultado);
    }

    @Test
    @DisplayName("PAGSEGURO deve aceitar status de tentativa SUCESSO")
    public void test06(){
        MetodoPagamento metodoPagamento = MetodoPagamento.PAGSEGURO;
        boolean resultado = metodoPagamento.contemStatus("SUCESSO");
        assertTrue(resultado);
    }


}