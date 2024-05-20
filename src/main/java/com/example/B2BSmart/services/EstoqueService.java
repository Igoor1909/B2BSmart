package com.example.B2BSmart.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.EstoqueRespository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EstoqueService {

    @Autowired
    EstoqueRespository estoqueRespository;

    // Método para buscar todos os produtos no estoque
    public List<Estoque> buscarProdutos() {
        return estoqueRespository.findAll();
    }

    // Método para buscar os produtos por fornecedor
    public List<Estoque> buscarPorFornecedor(Long id) throws Exception {
        try {
            Estoque fornecedor = estoqueRespository.findByFornecedor(id);
            fornecedor = (Estoque) Hibernate.unproxy(fornecedor);
            return Collections.singletonList(fornecedor);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    // Método para buscar os produtos por produto
    public List<Estoque> buscarPorProduto(Long id) throws Exception {
        try {
            Estoque estoque = estoqueRespository.findByProduto(id);
            estoque = (Estoque) Hibernate.unproxy(estoque);
            return Collections.singletonList(estoque);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    
    // Método para inserir um novo estoque ou atualizar a quantidade do estoque
    public Estoque inserirEstoque(Long id, Estoque obj) throws Exception{
        Estoque estoque;
        try {
            estoque = estoqueRespository.findByProduto(id);
            updateData(estoque, obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
        return estoqueRespository.save(estoque);
    }
    
    // Método para atualizar a quantidade do estoque
    public void updateData(Estoque entity, Estoque obj) {
        entity.setQuantidade(entity.getQuantidade() + obj.getQuantidade());
    }
    
    public void atualizarEstoque(Long idProduto, Integer quantidade) {
        // Busca o produto no estoque pelo id
        Estoque estoque = estoqueRespository.findByProduto(idProduto);

        // Atualiza a quantidade do produto no estoque
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);

        // Salva as alterações no banco de dados
        estoqueRespository.save(estoque);
    }
}
