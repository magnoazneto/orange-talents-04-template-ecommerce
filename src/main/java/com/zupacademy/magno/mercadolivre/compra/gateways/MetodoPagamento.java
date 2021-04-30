package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.StatusCompra;
import com.zupacademy.magno.mercadolivre.tentativapagamento.StatusTentativa;

import java.util.HashMap;

public enum MetodoPagamento {
    PAYPAL(new PaypalGateway()),
    PAGSEGURO(new PagseguroGateway());

    private final GatewayCompra gateway;

    MetodoPagamento(GatewayCompra gateway){
        this.gateway = gateway;
    }

    /**
     * Retorna o tipo de Gateway de pagamento selecionado no ato da compra
     * @return
     */
    public GatewayCompra getGateway() {
        return gateway;
    }

    @Override
    public String toString() {
        return "MetodoPagamento{" +
                "gateway=" + gateway +
                '}';
    }

    /**
     *  Avalia uma tentativa de pagamento fazendo um match entre o padrão dos gateways e do sistema
     * @param statusTentativaRecebido o resultado da tentativa de pagamento no padrão do gateway
     * @return True se o status recebido corresponder a SUCESSO. False em caso contrário.
     * @see StatusTentativa
     */
    public boolean avaliaTentativaPagamento(String statusTentativaRecebido) {
        HashMap<?, StatusTentativa> statusTentativa = this.getGateway().getStatusTentativa();
        StatusTentativa statusSistema = statusTentativa.get(statusTentativaRecebido);

        return statusSistema.equals(StatusTentativa.SUCESSO);
    }
}
