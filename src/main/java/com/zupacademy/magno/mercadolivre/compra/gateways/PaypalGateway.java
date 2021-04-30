package com.zupacademy.magno.mercadolivre.compra.gateways;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.compra.StatusCompra;
import com.zupacademy.magno.mercadolivre.tentativapagamento.StatusTentativa;

import java.util.HashMap;
import java.util.UUID;

public class PaypalGateway implements GatewayCompra {

    private HashMap<?, StatusTentativa> statusTentativa = new HashMap<String, StatusTentativa>(){{
        put("1", StatusTentativa.SUCESSO);
        put("0", StatusTentativa.FALHA);
    }};


    /**
     * Constrói uma URL de redirecionamento com base numa compra
     * @param compra uma compra valida do sistema
     * @return URL de redirect contendo o id da compra e uma url para retorno pós pagamento
     */
    @Override
    public String buildUrl(Compra compra) {
        UUID idCompra = compra.getUuid();
        String urlRetorno = "compra/"+compra.getId().toString()+"/retorno";
        return "paypal.com?buyerId={"+ idCompra + "}&redirectUrl={"+urlRetorno+"}";
    }

    public HashMap<?, StatusTentativa> possiveisStatusDoGateway() {
        return statusTentativa;
    }

    @Override
    public String toString() {
        return "PaypalGateway{" +
                "statusTentativa=" + statusTentativa +
                '}';
    }
}
