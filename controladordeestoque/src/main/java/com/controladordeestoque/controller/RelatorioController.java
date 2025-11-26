package com.controladordeestoque.controller;

import com.controladordeestoque.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para exposição dos relatórios de estoque.
 */
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/lista-precos")
    public ResponseEntity<List<Map<String, Object>>> listaPrecos() {
        return ResponseEntity.ok(relatorioService.listaDePrecos());
    }

    @GetMapping("/balanco")
    public ResponseEntity<Map<String, Object>> balanco() {
        return ResponseEntity.ok(relatorioService.balancoFisicoFinanceiro());
    }

    @GetMapping("/abaixo-minimo")
    public ResponseEntity<List<Map<String, Object>>> abaixoMinimo() {
        return ResponseEntity.ok(relatorioService.abaixoDaQuantidadeMinima());
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Map<String, Object>>> produtosPorCategoria() {
        return ResponseEntity.ok(relatorioService.quantidadeProdutosPorCategoria());
    }
}