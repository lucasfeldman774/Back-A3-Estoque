package com.controladordeestoque.repository;

import com.controladordeestoque.domain.Movimentacao;
import com.controladordeestoque.domain.enums.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m.produto.id, m.produto.nome, SUM(m.quantidadeMovimentada) " +
           "FROM Movimentacao m WHERE m.tipo = :tipo GROUP BY m.produto.id, m.produto.nome")
    List<Object[]> sumQuantidadesPorProduto(@Param("tipo") TipoMovimentacao tipo);

    // Lista ordenada por data (mais recentes primeiro), com paginação para limitar quantidade
    Page<Movimentacao> findAllByOrderByDataMovimentacaoDesc(Pageable pageable);

    void deleteByProdutoId(Long produtoId);
}