package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.tentativapagamento.StatusTentativa;

import java.util.HashMap;

public interface GatewayCompra {
    public String buildUrl(Compra compra);

    /**
     * Recupera o resultado da tentativa de pagamento no formato padrão do sistema.
     * A Chave do HashMap aponta para o valor equivalente aquele resultado no padrão do sistema
     * @see StatusTentativa
     * @return um tipo de Enum StatusTentativa
     */
    public HashMap<?, StatusTentativa> getStatusTentativa();
}
