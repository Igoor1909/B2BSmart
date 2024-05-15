package com.example.B2BSmart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.B2BSmart.entity.Pagamento;
import com.example.B2BSmart.services.PagamentoService;

@RestController
@RequestMapping("B2B/pagamento")
public class PagamentoController {
	
	@Autowired
	PagamentoService service;

	@GetMapping("/buscar")
	public List<Pagamento> buscarPagamentos(){
		return service.buscarPagamentos();
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> inserirPagamento(@RequestBody Pagamento obj) throws Exception{
		obj = service.inserirPagamento(obj);
		return ResponseEntity.ok("Pagamento cadastrado com sucesso!");
	}
	
	@PutMapping("/alterar/{id}")
	public ResponseEntity<String> alterarPagamento(@RequestBody Pagamento obj, @PathVariable Long id) throws Exception{
		obj = service.alterarPagamento(id, obj);
		return ResponseEntity.ok("Pagamento alterado com sucesso!");
	}
	
	public ResponseEntity<String> excluirPagamento(@PathVariable Long id) throws Exception{
		service.excluirPagamento(id);
		return ResponseEntity.ok("Pagamento excluido com sucesso!");
	}
}
