package com.controladordeestoque.service;

import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
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
        produtoRepository.deleteById(id);
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