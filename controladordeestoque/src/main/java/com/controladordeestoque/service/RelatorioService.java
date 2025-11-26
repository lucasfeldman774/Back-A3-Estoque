package com.controladordeestoque.service;

import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Serviço responsável por relatórios de preços, balanço e indicadores de estoque.
 */
@Service
@Transactional(readOnly = true)
public class RelatorioService {
    private final ProdutoRepository produtoRepository;

    public RelatorioService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Map<String, Object>> listaDePrecos() {
        List<Produto> produtos = produtoRepository.findAllByOrderByNomeAsc();
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Produto p : produtos) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("nome", p.getNome());
            item.put("precoUnitario", p.getPrecoUnitario());
            item.put("unidade", p.getUnidade());
            item.put("categoria", p.getCategoria().getNome());
            lista.add(item);
        }
        return lista;
    }

    public Map<String, Object> balancoFisicoFinanceiro() {
        List<Produto> produtos = produtoRepository.findAllByOrderByNomeAsc();
        List<Map<String, Object>> itens = new ArrayList<>();
        BigDecimal totalEstoque = BigDecimal.ZERO;

        for (Produto p : produtos) {
            BigDecimal totalProduto = p.getPrecoUnitario().multiply(BigDecimal.valueOf(p.getQuantidade()));
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("nome", p.getNome());
            item.put("quantidade", p.getQuantidade());
            item.put("valorUnitario", p.getPrecoUnitario());
            item.put("valorTotalProduto", totalProduto);
            itens.add(item);
            totalEstoque = totalEstoque.add(totalProduto);
        }

        Map<String, Object> relatorio = new LinkedHashMap<>();
        relatorio.put("itens", itens);
        relatorio.put("valorTotalEstoque", totalEstoque);
        return relatorio;
    }

    public List<Map<String, Object>> abaixoDaQuantidadeMinima() {
        List<Produto> produtos = produtoRepository.findAllByOrderByNomeAsc();
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getQuantidade() < p.getQuantidadeMinima()) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("nome", p.getNome());
                item.put("quantidadeMinima", p.getQuantidadeMinima());
                item.put("quantidadeEmEstoque", p.getQuantidade());
                lista.add(item);
            }
        }
        return lista;
    }

    public List<Map<String, Object>> quantidadeProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Set<Long>> agrupado = new LinkedHashMap<>();
        for (Produto p : produtos) {
            String categoria = p.getCategoria().getNome();
            agrupado.computeIfAbsent(categoria, k -> new HashSet<>()).add(p.getId());
        }
        List<Map<String, Object>> rel = new ArrayList<>();
        for (Map.Entry<String, Set<Long>> e : agrupado.entrySet()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("categoria", e.getKey());
            item.put("quantidadeProdutosDistintos", e.getValue().size());
            rel.add(item);
        }
        rel.sort(Comparator.comparing(m -> m.get("categoria").toString()));
        return rel;
    }
}