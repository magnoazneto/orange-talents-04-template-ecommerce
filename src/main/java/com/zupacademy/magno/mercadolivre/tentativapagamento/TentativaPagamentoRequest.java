package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TentativaPagamentoRequest {

    @NotNull
    private Long compraId;
    @NotBlank
    private String pagamentoPlataformaId;
    @NotBlank
    private String statusTentativa;

    @Deprecated
    public TentativaPagamentoRequest() {
    }

    public TentativaPagamentoRequest(@NotNull Long compraId,
                                     @NotBlank String pagamentoPlataformaId,
                                     @NotBlank String statusTentativa) {
        this.compraId = compraId;
        this.pagamentoPlataformaId = pagamentoPlataformaId;
        this.statusTentativa = statusTentativa;
    }

    public TentativaPagamento toModel(Compra compra){
        StatusTentativa statusTentativaSistema = compra.
                getMetodoPagamento().
                getGateway().
                possiveisStatusDoGateway().
                get(this.getStatusTentativa());

        return new TentativaPagamento(this.pagamentoPlataformaId,
                compra,
                statusTentativaSistema
                );
    }

    public Long getCompraId() {
        return compraId;
    }

    public String getPagamentoPlataformaId() {
        return pagamentoPlataformaId;
    }

    public String getStatusTentativa() {
        return statusTentativa;
    }
}
