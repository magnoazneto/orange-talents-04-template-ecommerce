package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.tentativapagamento.StatusTentativa;
import com.zupacademy.magno.mercadolivre.tentativapagamento.TentativaPagamento;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Compra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final UUID uuid = UUID.randomUUID();
    @NotNull @ManyToOne
    private Produto produto;
    @NotNull @ManyToOne
    private Usuario comprador;
    @NotNull @Positive
    private Integer quantidade;
    @Enumerated(EnumType.STRING)
    private StatusCompra status;
    @NotNull @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;
    @NotNull
    private BigDecimal valorCorrente;
    @OneToMany(mappedBy = "compra")
    private Set<TentativaPagamento> tentativasPagamento = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull Produto produto,
                  @NotNull Usuario comprador,
                  @NotNull MetodoPagamento gateway,
                  @NotNull BigDecimal valorCorrente,
                  @NotNull @Positive Integer quantidade) {
        this.produto = produto;
        this.comprador = comprador;
        this.metodoPagamento = gateway;
        this.valorCorrente = valorCorrente;
        this.quantidade = quantidade;
        this.status = StatusCompra.INICIADA;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public BigDecimal getValorCorrente() {
        return valorCorrente;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", produto=" + produto +
                ", comprador=" + comprador +
                ", quantidade=" + quantidade +
                ", status=" + status +
                ", metodoPagamento=" + metodoPagamento +
                ", valorCorrente=" + valorCorrente +
                '}';
    }

    /**
     * Altera um Status de compra para CONCLUIDO se possível
     * @throws ResponseStatusException com BAD_REQUEST em caso de falha
     * @see StatusCompra
     */
    public void concluirCompra() {
        for (TentativaPagamento tentativa : tentativasPagamento) {
            if (tentativa.getStatusTentativa().equals(StatusTentativa.SUCESSO)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Status da compra informada não pode mais ser alterado.");
            }
        }

        this.status = StatusCompra.CONCLUIDA;
    }
}
