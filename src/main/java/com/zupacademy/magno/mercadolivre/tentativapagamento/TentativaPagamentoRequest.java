package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import javax.validation.constraints.NotBlank;

public class TentativaPagamentoRequest {

    @NotBlank
    private String pagamentoPlataformaId;
    @NotBlank
    private String statusTentativa;

    public TentativaPagamentoRequest(@NotBlank String pagamentoPlataformaId,
                                     @NotBlank String statusCompra) {
        this.pagamentoPlataformaId = pagamentoPlataformaId;
        this.statusTentativa = statusCompra;
    }

    public TentativaPagamento toModel(Compra compra){
        StatusTentativa statusTentativaSistema = compra.
                getMetodoPagamento().
                getGateway().
                getStatusTentativa().
                get(this.getStatusTentativa());

        return new TentativaPagamento(this.pagamentoPlataformaId,
                compra,
                statusTentativaSistema
                );
    }

    public String getPagamentoPlataformaId() {
        return pagamentoPlataformaId;
    }

    public String getStatusTentativa() {
        return statusTentativa;
    }
}
