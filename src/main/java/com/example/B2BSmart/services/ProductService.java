package com.example.B2BSmart.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Product;
import com.example.B2BSmart.repository.ProductRepository;

//anotation responsavel por indicar que a classe é um serviço
@Service
public class ProductService {

	//anotation responsavel por injetar uma outra classe nesta
	@Autowired
	ProductRepository productRepository;
	
	//Metodo voltado a buscar a lista de produtos cadastrados no BD
	public List<Product> buscarProdutos(){
		return productRepository.findAll();
	}
	
	//Metodo voltado para cadastros de novos produtos no BD
	public Product inserirProduto(Product obj) {
		//Inserção da data de criação do produto
		obj.setDataCriacao(new Date());
		//criando objeto para criação do novo produto
		Product produtoNovo = productRepository.saveAndFlush(obj);
		return produtoNovo;
	}
	
	//Metodo voltado para alterar algum produto ja cadastrado no BD
	public Product alterarProduto(Product obj) {
		obj.setDataAtualizacao(new Date());
		return productRepository.saveAndFlush(obj);
	}
	
	//Metodo voltado para excluir produtos ja cadastrado no BD, buscando pelo seu ID
	public void excluirProduto(Long id) {
		// processo para buscar o produto pelo ID repassado no objeto
		Product obj = productRepository.findById(id).get();
		productRepository.delete(obj);
	}
	
	
	
}
