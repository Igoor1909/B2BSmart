package com.example.B2BSmart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Produto;
import com.example.B2BSmart.exceptions.EanExistException;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {

	// anotation responsavel por injetar uma outra classe nesta
	@Autowired
	ProdutoRepository productRepository;

	// Metodo voltado a buscar a lista de produtos cadastrados no BD
	public List<Produto> buscarProdutos() {
		return productRepository.findAll();
	}

	// Metodo voltado para cadastros de novos produtos no BD
	public Produto inserirProduto(Produto obj) throws Exception {
		
			//metodo voltado para verificar se há codigos eans iguais no banco de dados
			if (productRepository.findByCodigoEAN(obj.getCodigoEAN()) != null) {
				throw new EanExistException("Ja existe um codigo EAN para " + obj.getCodigoEAN() + " ");
			}
		// criando objeto para criação do novo produto
		Produto produtoNovo = productRepository.saveAndFlush(obj);
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
	    entity.setQuantidadeInicial(obj.getQuantidadeInicial());
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
