package com.zupacademy.magno.mercadolivre.compra.gateways;

import java.util.UUID;

public class PaypalGateway implements GatewayCompra {

    @Override
    public String buildUrl(UUID idCompra, String urlRetorno) {
        return "paypal.com?buyerId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }
}
