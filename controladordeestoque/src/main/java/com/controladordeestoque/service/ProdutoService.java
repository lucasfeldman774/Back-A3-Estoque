package com.controladordeestoque.service;

import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.repository.MovimentacaoRepository;
import com.controladordeestoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Regra de negócio para cadastro, atualização, exclusão e reajuste de preços de produtos.
 */
@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto existente = buscarPorId(id);
        existente.setNome(produto.getNome());
        existente.setPrecoUnitario(produto.getPrecoUnitario());
        existente.setUnidade(produto.getUnidade());
        existente.setQuantidade(produto.getQuantidade());
        existente.setQuantidadeMinima(produto.getQuantidadeMinima());
        existente.setQuantidadeMaxima(produto.getQuantidadeMaxima());
        existente.setCategoria(produto.getCategoria());
        return produtoRepository.save(existente);
    }

    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        if (produto.getQuantidade() != null && produto.getQuantidade() > 0) {
            throw new IllegalArgumentException("Não é possível excluir um produto com quantidade em estoque.");
        }

        movimentacaoRepository.deleteByProdutoId(id);
        produtoRepository.delete(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<Produto> listarOrdenadoPorNome() {
        return produtoRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Reajusta os preços de todos os produtos por um percentual (ex: 10.0 = 10%).
     */
    public void reajustarPrecos(double percentual) {
        BigDecimal fator = BigDecimal.valueOf(1.0 + (percentual / 100.0));
        List<Produto> produtos = produtoRepository.findAll();
        for (Produto p : produtos) {
            BigDecimal novoPreco = p.getPrecoUnitario().multiply(fator).setScale(2, RoundingMode.HALF_UP);
            p.setPrecoUnitario(novoPreco);
        }
        produtoRepository.saveAll(produtos);
    }
}