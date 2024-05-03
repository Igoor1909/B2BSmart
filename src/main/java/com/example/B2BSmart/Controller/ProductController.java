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

	// Método para buscar todos os produtos cadastrados
	@GetMapping(value = "/buscar")
	public List<Product> buscarProdutos() {
		return productService.buscarProdutos();
	}

	// Método para cadastrar um novo produto
	@PostMapping(value = "/cadastrar")
	public Product inserirProduto(@RequestBody Product obj) throws Exception {
		return productService.inserirProduto(obj);
	}

	// Método para alterar um produto existente
	@PutMapping(value = "/alterar/{id}")
	public ResponseEntity<Product> alterarProduto(@PathVariable Long id, @RequestBody Product obj) throws Exception {
		obj = productService.alterarProduto(obj, id);
		return ResponseEntity.ok().body(obj);
	}

	// Método para excluir um produto pelo seu ID
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> excluirProduto(@PathVariable("id") Long id) {
		productService.excluirProduto(id);
		return ResponseEntity.ok().build();
	}
}
