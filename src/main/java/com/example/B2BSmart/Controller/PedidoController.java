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
		return pedidoService.buscarTodos();
	}
	
	// Método para buscar os pedidos cadastrados por ID
	@GetMapping(value = "/buscar/{id}")
	public List<Pedido> buscarPorID(@PathVariable Long id) throws Exception {
	    return pedidoService.buscarPorID(id);
	}


	// Método para cadastrar um novo Pedido
	@PostMapping(value = "/registrar")
	public ResponseEntity<String> inserirPedido(@RequestBody Pedido obj) throws Exception {
		obj = pedidoService.inserirPedido(obj);
		return ResponseEntity.ok("Pedido emitido com sucesso!");
	}

	// Método para alterar um Pedido existente
	@PutMapping(value = "/alterar/{id}")
	public ResponseEntity<String> alterarPedido(@PathVariable Long id, @RequestBody Pedido obj) throws Exception {
		obj = pedidoService.alterarPedido(obj, id);
		return ResponseEntity.ok("Pedido alterado com sucesso!");
	}

	// Método para excluir um Pedido pelo seu ID
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> excluirPedido(@PathVariable("id") Long id) {
		pedidoService.excluirPedido(id);
		return ResponseEntity.ok("Pedido excluido com sucesso!");
	}
	
	// Metodo para trocar o status do pedido para cancelado a partir de seu ID
	@PutMapping(value = "/cancelar/{id}")
	public ResponseEntity<String> cancelarPedido(@PathVariable Long id, @RequestBody Pedido obj) throws Exception {
		obj = pedidoService.cancelarPedido(obj, id);
		return ResponseEntity.ok("Pedido cancelado com sucesso!");
	}
	
	// Metodo para trocar o status do pedido para EM_TRANSITO a partir de seu ID
	@PutMapping(value = "/enviar/{id}")
	public ResponseEntity<String> enviarPedido(@PathVariable Long id, @RequestBody Pedido obj) throws Exception {
	    obj = pedidoService.enviarPedido(obj, id);
	    return ResponseEntity.ok("Pedido enviado com sucesso!");
	}

	
	// Metodo para trocar o status do pedido para FINALIZADO a partir de seu ID
	@PutMapping(value = "/finalizar/{id}")
	public ResponseEntity<String> finalizarPedido(@PathVariable Long id, @RequestBody Pedido obj) throws Exception{
		obj = pedidoService.finalizarPedido(obj, id);
		return ResponseEntity.ok("Pedido finalizado com sucesso!");
	}
}

