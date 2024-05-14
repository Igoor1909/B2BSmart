package com.example.B2BSmart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.services.EstoqueService;

@RestController
@RequestMapping("B2B/estoque")
public class EstoqueController {
	
	@Autowired
	EstoqueService estoqueService;

	@GetMapping(value = "/buscar")
	public List<Estoque> buscarProdutos(){
		return estoqueService.buscarProdutos();
	}
	
	@GetMapping(value = "/buscar/produto/{id}")
	public List<Estoque> buscarProdutosPorID(@PathVariable Long id) throws Exception{
		return estoqueService.buscarPorProduto(id);
	}
	
	@GetMapping(value = "/buscar/produto/fornecedor/{id}")
	public List<Estoque> buscarFornecedorPorID(@PathVariable Long id) throws Exception{
		return estoqueService.buscarPorFornecedor(id);
	}
	
	@PostMapping(value = "/inserir/produto/{id}")
	public ResponseEntity<String> alterarEstoque(@PathVariable Long id, @RequestBody Estoque obj) throws Exception{
		obj = estoqueService.inserirEstoque(id, obj);
		return ResponseEntity.ok("Estoque inserido com sucesso!");
	}
	
	
}
