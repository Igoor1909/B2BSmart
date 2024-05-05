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

import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.services.PedidoService;

@RestController
@RequestMapping("B2B/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	// Método para buscar todos os Pedidos cadastrados
	@GetMapping(value = "/buscar")
	public List<Pedido> buscarPedidos() {
		return pedidoService.buscarPedidos();
	}

	// Método para cadastrar um novo Pedido
	@PostMapping(value = "/registrar")
	public Pedido inserirPedido(@RequestBody Pedido obj) throws Exception {
		return pedidoService.inserirPedido(obj);
	}

	// Método para alterar um Pedido existente
	@PutMapping(value = "/alterar/{id}")
	public ResponseEntity<Pedido> alterarPedido(@PathVariable Long id, @RequestBody Pedido obj) throws Exception {
		obj = pedidoService.alterarPedido(obj, id);
		return ResponseEntity.ok().body(obj);
	}

	// Método para excluir um Pedido pelo seu ID
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> excluirPedido(@PathVariable("id") Long id) {
		pedidoService.excluirPedido(id);
		return ResponseEntity.ok().build();
	}
}
