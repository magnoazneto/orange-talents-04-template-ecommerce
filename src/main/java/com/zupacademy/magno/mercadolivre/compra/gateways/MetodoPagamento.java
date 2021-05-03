package com.zupacademy.magno.mercadolivre.compra.gateways;

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

    /**
     *  Avalia um Status de tentativa com base nos padrões do sistema
     * @param statusTentativaRecebido o resultado da tentativa de pagamento no padrão do gateway
     * @return True se o status recebido corresponder a SUCESSO. False em caso contrário.
     * @see StatusTentativa
     */
    public boolean tentativaBemSucedida(String statusTentativaRecebido) {
        HashMap<?, StatusTentativa> possiveisStatus = this.getGateway().possiveisStatusDoGateway();
        StatusTentativa statusSistema = possiveisStatus.get(statusTentativaRecebido);

        return statusSistema.equals(StatusTentativa.SUCESSO);
    }

    public boolean contemStatus(String statusTentativaRecebido){
        return gateway.possiveisStatusDoGateway().containsKey(statusTentativaRecebido);
    }
}
