package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import java.util.UUID;

public interface GatewayCompra {
    public String buildUrl(Compra compra);
}
