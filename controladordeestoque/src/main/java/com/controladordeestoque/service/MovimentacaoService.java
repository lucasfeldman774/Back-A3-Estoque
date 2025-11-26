package com.controladordeestoque.service;

import com.controladordeestoque.domain.Movimentacao;
import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.domain.enums.TipoMovimentacao;
import com.controladordeestoque.repository.MovimentacaoRepository;
import com.controladordeestoque.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Regra de negócio para registrar movimentações e gerar relatórios básicos de movimentação.
 */
@Service
@Transactional
public class MovimentacaoService {
    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Map<String, Object> registrarMovimentacao(Long produtoId, Integer quantidade, TipoMovimentacao tipo, LocalDateTime data) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + produtoId));

        if (tipo == TipoMovimentacao.SAIDA) {
            int novoSaldo = produto.getQuantidade() - quantidade;
            if (novoSaldo < 0) {
                throw new IllegalArgumentException("Saída excede o saldo disponível");
            }
            produto.setQuantidade(novoSaldo);
        } else {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        }

        produtoRepository.save(produto);

        Movimentacao m = new Movimentacao();
        m.setProduto(produto);
        m.setQuantidadeMovimentada(quantidade);
        m.setTipo(tipo);
        m.setDataMovimentacao(data != null ? data : LocalDateTime.now());
        movimentacaoRepository.save(m);

        String aviso = null;
        if (tipo == TipoMovimentacao.SAIDA && produto.getQuantidade() < produto.getQuantidadeMinima()) {
            aviso = "Quantidade abaixo do mínimo. Providenciar compra.";
        } else if (tipo == TipoMovimentacao.ENTRADA && produto.getQuantidade() > produto.getQuantidadeMaxima()) {
            aviso = "Quantidade acima do máximo. Evitar novas compras.";
        }

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("movimentacaoId", m.getId());
        resp.put("produtoId", produto.getId());
        resp.put("produtoNome", produto.getNome());
        resp.put("dataMovimentacao", m.getDataMovimentacao());
        resp.put("quantidadeMovimentada", m.getQuantidadeMovimentada());
        resp.put("tipo", m.getTipo());
        resp.put("saldoAtual", produto.getQuantidade());
        if (aviso != null) resp.put("aviso", aviso);
        return resp;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> relatorioTopMovimentacoes() {
        Map<String, Object> result = new LinkedHashMap<>();
        Object[] topEntrada = encontrarTopoPorTipo(TipoMovimentacao.ENTRADA);
        Object[] topSaida = encontrarTopoPorTipo(TipoMovimentacao.SAIDA);
        result.put("topEntrada", toMap(topEntrada));
        result.put("topSaida", toMap(topSaida));
        return result;
    }

    private Object[] encontrarTopoPorTipo(TipoMovimentacao tipo) {
        List<Object[]> lista = movimentacaoRepository.sumQuantidadesPorProduto(tipo);
        return lista.stream().max(Comparator.comparingLong(o -> ((Number) o[2]).longValue())).orElse(null);
    }

    private Map<String, Object> toMap(Object[] arr) {
        if (arr == null) return Collections.emptyMap();
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("produtoId", arr[0]);
        m.put("produtoNome", arr[1]);
        m.put("quantidadeTotal", arr[2]);
        return m;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> listarDetalhes() {
        List<Movimentacao> movimentos = movimentacaoRepository.findAll();
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Movimentacao mv : movimentos) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", mv.getId());
            item.put("produtoId", mv.getProduto().getId());
            item.put("produtoNome", mv.getProduto().getNome());
            item.put("dataMovimentacao", mv.getDataMovimentacao());
            item.put("quantidadeMovimentada", mv.getQuantidadeMovimentada());
            item.put("tipo", mv.getTipo());
            lista.add(item);
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> listarRecentes(int limit) {
        Page<Movimentacao> page = movimentacaoRepository.findAllByOrderByDataMovimentacaoDesc(PageRequest.of(0, Math.max(limit, 1)));
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Movimentacao mv : page.getContent()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", mv.getId());
            item.put("produtoId", mv.getProduto().getId());
            item.put("produtoNome", mv.getProduto().getNome());
            item.put("dataMovimentacao", mv.getDataMovimentacao());
            item.put("quantidadeMovimentada", mv.getQuantidadeMovimentada());
            item.put("tipo", mv.getTipo());
            lista.add(item);
        }
        return lista;
    }
}