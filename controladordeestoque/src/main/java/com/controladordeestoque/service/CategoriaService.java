package com.controladordeestoque.service;

import com.controladordeestoque.domain.Categoria;
import com.controladordeestoque.repository.CategoriaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        Categoria existente = buscarPorId(id);
        existente.setNome(categoria.getNome());
        existente.setTamanho(categoria.getTamanho());
        existente.setEmbalagem(categoria.getEmbalagem());
        return categoriaRepository.save(existente);
    }

    public void excluir(Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            // categoria relacionada a produtos não pode ser removida
            throw new IllegalArgumentException("Não é possível excluir a categoria pois existem produtos vinculados.");
        }
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }
}