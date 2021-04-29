package com.zupacademy.magno.mercadolivre.compra.gateways;

public enum MetodoPagamento {
    PAYPAL(new PaypalGateway()),
    PAGSEGURO(new PagseguroGateway());

    private final GatewayCompra gateway;

    MetodoPagamento(GatewayCompra gateway){
        this.gateway = gateway;
    }

    public GatewayCompra getGateway() {
        return gateway;
    }

    @Override
    public String toString() {
        return "MetodoPagamento{" +
                "gateway=" + gateway +
                '}';
    }
}
