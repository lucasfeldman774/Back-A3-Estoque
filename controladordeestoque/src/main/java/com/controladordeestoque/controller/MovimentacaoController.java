package com.controladordeestoque.controller;

import com.controladordeestoque.domain.enums.TipoMovimentacao;
import com.controladordeestoque.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {
    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(@RequestParam("produtoId") Long produtoId,
                                                         @RequestParam("quantidade") Integer quantidade,
                                                         @RequestParam("tipo") TipoMovimentacao tipo) {
        Map<String, Object> resp = movimentacaoService.registrarMovimentacao(produtoId, quantidade, tipo, LocalDateTime.now());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/top")
    public ResponseEntity<Map<String, Object>> topMovimentacoes() {
        return ResponseEntity.ok(movimentacaoService.relatorioTopMovimentacoes());
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listar() {
        return ResponseEntity.ok(movimentacaoService.listarDetalhes());
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<Map<String, Object>>> listarRecentes(@RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(movimentacaoService.listarRecentes(limit));
    }
}