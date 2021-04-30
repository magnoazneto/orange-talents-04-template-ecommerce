package com.zupacademy.magno.mercadolivre.tentativapagamento;

import javax.validation.constraints.NotBlank;

public class TentativaPagamentoRequest {

    @NotBlank
    private String idPagamentoPlataforma;
    @NotBlank
    private String statusCompra;

    public TentativaPagamentoRequest(@NotBlank String idPagamentoPlataforma,
                                     @NotBlank String statusCompra) {
        this.idPagamentoPlataforma = idPagamentoPlataforma;
        this.statusCompra = statusCompra;
    }

    public String getIdPagamentoPlataforma() {
        return idPagamentoPlataforma;
    }

    public String getStatusCompra() {
        return statusCompra;
    }
}
