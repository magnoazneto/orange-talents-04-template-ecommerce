package com.zupacademy.magno.mercadolivre.compra.gateways;

import java.util.UUID;

public interface GatewayCompra {
    public String buildUrl(UUID idCompra, String urlRetorno);
}
