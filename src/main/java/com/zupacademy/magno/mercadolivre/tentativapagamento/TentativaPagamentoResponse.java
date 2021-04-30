package com.zupacademy.magno.mercadolivre.tentativapagamento;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TentativaPagamentoResponse {

    private final Long id;
    private final String pagamentoPlataformaId;
    private final Long compraId;
    private final String statusTentativa;
    private final String statusAtualCompra;

    public TentativaPagamentoResponse(@NotNull @Valid TentativaPagamento tentativaPagamento) {
        this.id = tentativaPagamento.getId();
        this.pagamentoPlataformaId = tentativaPagamento.getPagamentoPlataformaId();
        this.compraId = tentativaPagamento.getCompra().getId();
        this.statusTentativa = tentativaPagamento.getStatusTentativa().toString();
        this.statusAtualCompra = tentativaPagamento.getCompra().getStatus().toString();
    }

    public Long getId() {
        return id;
    }

    public String getPagamentoPlataformaId() {
        return pagamentoPlataformaId;
    }

    public Long getCompraId() {
        return compraId;
    }

    public String getStatusTentativa() {
        return statusTentativa;
    }

    public String getStatusAtualCompra() {
        return statusAtualCompra;
    }
}
