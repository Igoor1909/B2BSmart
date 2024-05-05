package com.example.B2BSmart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

	// anotation responsavel por injetar uma outra classe nesta
	@Autowired
	PedidoRepository Repository;

	// Metodo voltado a buscar a lista de Pedidos cadastrados no BD
	public List<Pedido> buscarPedidos() {
		return Repository.findAll();
	}

	// Metodo voltado para cadastros de novos Pedidos no BD
	public Pedido inserirPedido(Pedido obj) throws Exception {
		
		// criando objeto para criação do novo Pedido
		Pedido PedidoNovo = Repository.saveAndFlush(obj);
		return PedidoNovo;
	}

	// Metodo voltado para alterar algum Pedido ja cadastrado no BD
	public Pedido alterarPedido(Pedido obj, Long id) throws Exception {
		Pedido Pedido;
		try {
			Pedido = Repository.getReferenceById(id);
			updateData(Pedido, obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		return Repository.save(Pedido);
	}

	// metodo para fazer a troca de informações no momento de edição do objeto
	public void updateData(Pedido entity, Pedido obj) {
		entity.setCliente(obj.getCliente());
		entity.setFornecedor(obj.getFornecedor());
	}

	// Metodo voltado para excluir Pedidos ja cadastrado no BD, buscando pelo seu
	// ID
	public void excluirPedido(Long id) {
		Pedido Pedido;
		
		try {
			Pedido = Repository.findById(id).get();
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		// processo para buscar o Pedido pelo ID repassado no objeto
		Repository.delete(Pedido);
	}

}
