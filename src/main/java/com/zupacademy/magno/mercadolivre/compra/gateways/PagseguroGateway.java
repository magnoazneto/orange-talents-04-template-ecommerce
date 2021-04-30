package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.tentativapagamento.StatusTentativa;

import java.util.HashMap;
import java.util.UUID;

public class PagseguroGateway implements GatewayCompra {

    private HashMap<?, StatusTentativa> statusTentativa = new HashMap<String, StatusTentativa>(){{
        put("SUCESSO", StatusTentativa.SUCESSO);
        put("FALHA", StatusTentativa.FALHA);
    }};

    @Override
    public String buildUrl(Compra compra) {
        UUID idCompra = compra.getUuid();
        String urlRetorno = "compra/"+compra.getId().toString()+"/retorno";
        return "pagseguro.com?returnId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }

    @Override
    public HashMap<?, StatusTentativa> possiveisStatusDoGateway() {
        return statusTentativa;
    }

    @Override
    public String toString() {
        return "PagseguroGateway{" +
                "statusTentativa=" + statusTentativa +
                '}';
    }
}
