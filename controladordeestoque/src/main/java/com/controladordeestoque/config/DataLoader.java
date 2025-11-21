package com.controladordeestoque.config;

import com.controladordeestoque.domain.Categoria;
import com.controladordeestoque.domain.Produto;
import com.controladordeestoque.domain.enums.Embalagem;
import com.controladordeestoque.domain.enums.Tamanho;
import com.controladordeestoque.repository.CategoriaRepository;
import com.controladordeestoque.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        return args -> {
            if (categoriaRepository.count() == 0) {
                Categoria limpeza = new Categoria();
                limpeza.setNome("Limpeza");
                limpeza.setTamanho(Tamanho.MEDIO);
                limpeza.setEmbalagem(Embalagem.PLASTICO);
                limpeza = categoriaRepository.save(limpeza);

                Categoria enlatados = new Categoria();
                enlatados.setNome("Enlatados");
                enlatados.setTamanho(Tamanho.PEQUENO);
                enlatados.setEmbalagem(Embalagem.LATA);
                enlatados = categoriaRepository.save(enlatados);

                Categoria vegetais = new Categoria();
                vegetais.setNome("Vegetais");
                vegetais.setTamanho(Tamanho.GRANDE);
                vegetais.setEmbalagem(Embalagem.PLASTICO);
                vegetais = categoriaRepository.save(vegetais);

                if (produtoRepository.count() == 0) {
                    Produto detergente = new Produto();
                    detergente.setNome("Detergente");
                    detergente.setPrecoUnitario(new BigDecimal("4.50"));
                    detergente.setUnidade("unidade");
                    detergente.setQuantidade(10);
                    detergente.setQuantidadeMinima(5);
                    detergente.setQuantidadeMaxima(50);
                    detergente.setCategoria(limpeza);
                    produtoRepository.save(detergente);

                    Produto milhoLata = new Produto();
                    milhoLata.setNome("Milho em Lata");
                    milhoLata.setPrecoUnitario(new BigDecimal("6.99"));
                    milhoLata.setUnidade("lata");
                    milhoLata.setQuantidade(80);
                    milhoLata.setQuantidadeMinima(20);
                    milhoLata.setQuantidadeMaxima(100);
                    milhoLata.setCategoria(enlatados);
                    produtoRepository.save(milhoLata);

                    Produto alface = new Produto();
                    alface.setNome("Alface");
                    alface.setPrecoUnitario(new BigDecimal("3.20"));
                    alface.setUnidade("maço");
                    alface.setQuantidade(12);
                    alface.setQuantidadeMinima(10);
                    alface.setQuantidadeMaxima(40);
                    alface.setCategoria(vegetais);
                    produtoRepository.save(alface);
                }
            }
        };
    }
}