package com.controladordeestoque.controller;

import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Validated @RequestBody Produto produto) {
        Produto criado = produtoService.criar(produto);
        return ResponseEntity.created(URI.create("/produtos/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Validated @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.atualizar(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarOrdenado() {
        return ResponseEntity.ok(produtoService.listarOrdenadoPorNome());
    }

    @PostMapping("/reajustar-precos")
    public ResponseEntity<Void> reajustarPrecos(@RequestParam("percentual") double percentual) {
        produtoService.reajustarPrecos(percentual);
        return ResponseEntity.noContent().build();
    }
}