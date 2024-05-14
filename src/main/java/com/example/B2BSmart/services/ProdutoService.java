package com.example.B2BSmart.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.entity.Produto;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.EstoqueRespository;
import com.example.B2BSmart.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {

	// anotation responsavel por injetar uma outra classe nesta
	@Autowired
	ProdutoRepository productRepository;
	
	@Autowired
	EstoqueRespository estoqueRespository;

	// Metodo voltado a buscar a lista de produtos cadastrados no BD
	public List<Produto> buscarProdutos() {
		return productRepository.findAll();
	}

	public List<Produto> buscarPorFornecedor(Long id) throws Exception {
		try {
			Produto fornecedor = productRepository.findByFornecedor(id);
			fornecedor = (Produto) Hibernate.unproxy(fornecedor);
			return Collections.singletonList(fornecedor);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	// Metodo voltado para cadastros de novos produtos no BD
	public Produto inserirProduto(Produto obj) throws Exception {
		Produto produtoNovo = productRepository.saveAndFlush(obj);
	    
	    // Criando um novo registro de estoque
	    Estoque estoque = new Estoque();
	    estoque.setId_produto(produtoNovo);
	    estoque.setId_fornecedor(produtoNovo.getFornecedor()); // Supondo que o fornecedor do produto seja o mesmo do estoque
	    estoque.setQuantidade(0); // Define a quantidade inicial como 0
	    
	    // Salvando o registro de estoque
	    estoqueRespository.save(estoque);
	    
	    return produtoNovo;
	}

	// Metodo voltado para alterar algum produto ja cadastrado no BD
	public Produto alterarProduto(Produto obj, Long id) throws Exception {
		Produto produto;
		try {
			produto = productRepository.getReferenceById(id);
			updateData(produto, obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		return productRepository.save(produto);
	}

	// metodo para fazer a troca de informações no momento de edição do objeto
	public void updateData(Produto entity, Produto obj) {
		entity.setNome(obj.getNome());
		entity.setPreco(obj.getPreco());
		entity.setMarca(obj.getMarca());
		entity.setDescricao(obj.getDescricao());
		entity.setCodigoEAN(obj.getCodigoEAN());
		entity.setCategorias(obj.getCategorias());
	}

	// Metodo voltado para excluir produtos ja cadastrado no BD, buscando pelo seu
	// ID
	public void excluirProduto(Long id) {
		Produto produto;

		try {
			produto = productRepository.findById(id).get();

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		// processo para buscar o produto pelo ID repassado no objeto
		productRepository.delete(produto);
	}

}
