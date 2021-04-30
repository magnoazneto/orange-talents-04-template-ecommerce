package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class TentativaPagamento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String pagamentoPlataformaId;
    @ManyToOne
    private Compra compra;
    @NotNull @Enumerated(EnumType.STRING)
    private StatusTentativa statusTentativa;
    private LocalDateTime instanteTentativa;

    @Deprecated
    public TentativaPagamento() {
    }

    public TentativaPagamento(@NotBlank String pagamentoPlataformaId,
                              @NotNull @Valid Compra compra,
                              @NotNull StatusTentativa statusTentativa) {
        this.pagamentoPlataformaId = pagamentoPlataformaId;
        this.statusTentativa = statusTentativa;
        this.compra = compra;
        this.instanteTentativa = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPagamentoPlataformaId() {
        return pagamentoPlataformaId;
    }

    public Compra getCompra() {
        return compra;
    }

    public StatusTentativa getStatusTentativa() {
        return statusTentativa;
    }
}
