package com.controladordeestoque.domain;

import com.controladordeestoque.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Entidade que registra as entradas e saídas de produtos do estoque.
 */
@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Produto produto;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataMovimentacao;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer quantidadeMovimentada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }
    public Integer getQuantidadeMovimentada() { return quantidadeMovimentada; }
    public void setQuantidadeMovimentada(Integer quantidadeMovimentada) { this.quantidadeMovimentada = quantidadeMovimentada; }
    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
}