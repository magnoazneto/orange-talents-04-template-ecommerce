package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import java.util.UUID;

public class PagseguroGateway implements GatewayCompra {

    @Override
    public String buildUrl(Compra compra) {
        UUID idCompra = compra.getUuid();
        String urlRetorno = "compra/"+compra.getId().toString()+"/retorno";
        return "pagseguro.com?returnId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }
}
