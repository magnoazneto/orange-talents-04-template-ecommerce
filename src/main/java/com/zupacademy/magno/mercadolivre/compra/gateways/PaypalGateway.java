package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import java.util.UUID;

public class PaypalGateway implements GatewayCompra {

    @Override
    public String buildUrl(Compra compra) {
        UUID idCompra = compra.getUuid();
        String urlRetorno = "compra/"+compra.getId().toString()+"/retorno";
        return "paypal.com?buyerId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }
}
