package com.example.B2BSmart.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.entity.StatusPedido;
import com.example.B2BSmart.exceptions.QuantidadeInsuficienteException;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.exceptions.StatusEnviadoException;
import com.example.B2BSmart.repository.EstoqueRespository;
import com.example.B2BSmart.repository.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

	// anotation responsavel por injetar uma outra classe nesta
	@Autowired
	PedidoRepository Repository;

	@Autowired
	EstoqueRespository estoqueRespository;

	// metodo voltado para buscar todos os conteudos da lista de pedidos
	public List<Pedido> buscarTodos() {
		return Repository.findAll();
	}

	// Metodo para buscar pedido apartir de seu ID
	public List<Pedido> buscarPorID(Long id) throws Exception {
		try {
			// Obtém o pedido correspondente ao ID fornecido
			Pedido pedido = Repository.getReferenceById(id);
			// Desproxifica o pedido
			pedido = (Pedido) Hibernate.unproxy(pedido);
			// Retorna o pedido
			return Collections.singletonList(pedido);
		} catch (EntityNotFoundException e) {
			// Se o pedido não for encontrado, lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException(id);
		}
	}

	// Metodo voltado para cadastros de novos Pedidos no BD
	public Pedido inserirPedido(Pedido obj) throws Exception {
		// Salvar o pedido
		Pedido pedidoNovo = Repository.save(obj);

		return pedidoNovo;
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
		entity.setStatusPedido(obj.getStatusPedido());
		entity.setProduto(obj.getProduto());
		entity.setQuantidade(obj.getQuantidade());
		entity.setPagamento(obj.getPagamento());
	}

	public void diminuirEstoque(Estoque entity, Pedido obj) throws QuantidadeInsuficienteException {
	    // Verifica se a quantidade em estoque é suficiente para o pedido
	    if (entity.getQuantidade() < obj.getQuantidade()) {
	        throw new QuantidadeInsuficienteException("Não há estoque suficiente para realizar o pedido.");
	    }
	    // Diminui a quantidade do estoque
	    entity.setQuantidade(entity.getQuantidade() - obj.getQuantidade());
	}


	public void retornarEstoque(Estoque entity, Pedido obj) {
		entity.setQuantidade(entity.getQuantidade() + obj.getQuantidade());
	}

	// Método para cancelar um pedido
	public Pedido cancelarPedido(Pedido obj, Long id) throws Exception {
		try {
			// Obtém o pedido correspondente ao ID fornecido
			Pedido pedido = Repository.getReferenceById(id);
			// Verifica se o pedido está em trânsito
			if (pedido.getStatusPedido() == StatusPedido.EM_TRANSPORTE) {
				// Lança uma exceção se o pedido estiver em trânsito
				throw new StatusEnviadoException("Pedido em trânsito, impossível cancelar");
				// Verifica se o pedido está finalizado
			} else if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
				// Lança uma exceção se o pedido estiver finalizado
				throw new StatusEnviadoException("Pedido finalizado, impossivel cancelar");
				// Verifica se o pedido já esta cancelado
			} else if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
				// Lança uma exceção se o pedido ja estiver cancelado
				throw new StatusEnviadoException("Pedido ja cancelado!");
			}

			// Encontra o estoque correspondente ao produto do pedido cancelado
			Estoque estoque = estoqueRespository.findByProduto(obj.getProduto().getId());
			// Atualiza a quantidade do estoque usando o método retornarEstoque
			retornarEstoque(estoque, obj);
			// Salva as alterações no estoque no banco de dados
			estoqueRespository.save(estoque);
			// Define o status do pedido como CANCELADO
			pedido.setStatusPedido(StatusPedido.CANCELADO);
			
			// Salva as alterações no banco de dados
			Pedido pedidoCancelado = Repository.save(pedido);
			return pedidoCancelado;
		} catch (EntityNotFoundException e) {
			// Se o pedido não for encontrado, lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException(id);
		}
	}

	public Pedido enviarPedido(Pedido obj, Long id) throws Exception {
		try {
			// Obtém o pedido correspondente ao ID fornecido
			Pedido pedido = Repository.getReferenceById(id);
			// Verifica se o pedido já está em transporte
			if (pedido.getStatusPedido() == StatusPedido.EM_TRANSPORTE) {
				// Lança uma exceção se o pedido já estiver em transporte
				throw new StatusEnviadoException("Pedido já em transporte!");
			} else if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
				// Lança uma exceção se o pedido já estiver finalizado
				throw new StatusEnviadoException("Pedido já finalizado!");
			} else if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
				// Lança uma exceção se o pedido já estiver cancelado
				throw new StatusEnviadoException("Pedido já cancelado");
			}
			Estoque estoque = estoqueRespository.findByProduto(obj.getProduto().getId());
			diminuirEstoque(estoque, obj);
			estoqueRespository.save(estoque);
			// Define o status do pedido como EM_TRANSPORTE
			pedido.setStatusPedido(StatusPedido.EM_TRANSPORTE);
			// Salva as alterações no banco de dados
			Pedido pedidoEnviado = Repository.save(pedido);
			return pedidoEnviado;
		} catch (EntityNotFoundException e) {
			// Se o pedido não for encontrado, lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException(id);
		}
	}

	public Pedido finalizarPedido(Pedido obj, Long id) throws Exception {
		try {
			// Obtém o pedido correspondente ao ID fornecido
			Pedido pedido = Repository.getReferenceById(id);

			// Verifica se o pedido ainda está na fase de solicitação
			if (pedido.getStatusPedido() == StatusPedido.SOLICITADO) {
				// Lança uma exceção se o pedido ainda não saiu para entrega
				throw new StatusEnviadoException("Pedido ainda não saiu para entrega");
			} else if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
				// Lança uma exceção se o pedido já estiver cancelado
				throw new StatusEnviadoException("Pedido já cancelado");
			} else if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
				// Lança uma exceção se o pedido já estiver finalizado
				throw new StatusEnviadoException("Pedido já finalizado!");
			}
			
			// Define o status do pedido como FINALIZADO
			pedido.setStatusPedido(StatusPedido.FINALIZADO);

			// Salva as alterações no banco de dados
			return Repository.save(pedido);
		} catch (EntityNotFoundException e) {
			// Se o pedido não for encontrado, lança uma exceção de recurso não encontrado
			throw new ResourceNotFoundException(id);
		}
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
