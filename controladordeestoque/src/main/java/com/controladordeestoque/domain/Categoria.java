package com.controladordeestoque.domain;

import com.controladordeestoque.domain.enums.Embalagem;
import com.controladordeestoque.domain.enums.Tamanho;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que agrupa produtos por tamanho e tipo de embalagem.
 */
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tamanho tamanho;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Embalagem embalagem;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Tamanho getTamanho() { return tamanho; }
    public void setTamanho(Tamanho tamanho) { this.tamanho = tamanho; }
    public Embalagem getEmbalagem() { return embalagem; }
    public void setEmbalagem(Embalagem embalagem) { this.embalagem = embalagem; }
}