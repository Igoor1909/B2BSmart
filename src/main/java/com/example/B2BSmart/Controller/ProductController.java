package com.example.B2BSmart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.B2BSmart.entity.Product;
import com.example.B2BSmart.services.ProductService;

@RestController
@RequestMapping("B2B/produto")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	//anotation voltado para apresentar tudo que foi registrado
	@GetMapping("/")
	//metodo voltado para apresentar todos os produtos cadastrados em formato de lista
	public List<Product> buscarProdutos(){
		return productService.buscarProdutos();
	}
	
	//anotation voltado para inserir novo produto no BD através do metodo JSON
	@PostMapping("/")
	public Product inserirProduto(@RequestBody Product obj) {
		return productService.inserirProduto(obj);
	}
	
	//anotation voltado para alterar produtos ja cadastrados através do metodo JSON
	@PutMapping("/{id}")
	public Product alterarProduto(@RequestBody Product obj) {
		return productService.alterarProduto(obj);
	}
	
	//anotation voltado para deletar o produto pelo seu ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirProduto(@PathVariable("id")Long id){
		productService.excluirProduto(id);
		return ResponseEntity.ok().build();
	}
}
