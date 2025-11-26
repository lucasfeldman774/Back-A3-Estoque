package com.controladordeestoque.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Entidade que representa um produto controlado pelo estoque.
 */
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @NotBlank
    @Column(nullable = false)
    private String unidade;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidadeMinima;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidadeMaxima;

    @NotNull
    @ManyToOne(optional = false)
    private Categoria categoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Integer getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(Integer quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }
    public Integer getQuantidadeMaxima() { return quantidadeMaxima; }
    public void setQuantidadeMaxima(Integer quantidadeMaxima) { this.quantidadeMaxima = quantidadeMaxima; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}