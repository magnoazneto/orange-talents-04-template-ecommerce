package com.zupacademy.magno.mercadolivre.compra.gateways;

import java.util.UUID;

public class PagseguroGateway implements GatewayCompra {

    @Override
    public String buildUrl(UUID idCompra, String urlRetorno) {
        return "pagseguro.com?returnId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }
}
