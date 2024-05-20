package com.example.B2BSmart.services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.entity.ItemPedido;
import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.entity.StatusPedido;
import com.example.B2BSmart.exceptions.QuantidadeInsuficienteException;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.exceptions.StatusEnviadoException;
import com.example.B2BSmart.repository.EstoqueRespository;
import com.example.B2BSmart.repository.ItemPedidoRepository;
import com.example.B2BSmart.repository.PedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

	// anotation responsavel por injetar uma outra classe nesta
	@Autowired
	PedidoRepository Repository;

	@Autowired
	EstoqueRespository estoqueRespository;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	@Autowired
	ItemPedidoService itemPedidoService;

	@Autowired
	EstoqueService estoqueService;

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

	@Transactional
	public Pedido inserirPedido(Pedido pedido) throws Exception {
		// Associar cada item ao pedido antes de salvar
		for (ItemPedido item : pedido.getItens()) {
			item.setIdPedido(pedido);
		}

		// Calcular o valor total dos itens
		calcularValorTotalItens(pedido);

		// Calcular o total da venda
		calcularTotalVenda(pedido);

		// Salvar o pedido, que também salva os itens devido ao cascade
		Pedido pedidoNovo = pedidoRepository.save(pedido);

		return pedidoNovo;
	}

	private void calcularTotalVenda(Pedido pedido) {
		BigDecimal totalVenda = BigDecimal.ZERO;
		for (ItemPedido item : pedido.getItens()) {
			totalVenda = totalVenda.add(item.getValorTotal());
		}
		pedido.setTotalVenda(totalVenda);
	}

	// Metodo voltado para alterar algum Pedido ja cadastrado no BD
	@Transactional
	public Pedido alterarPedido(Pedido obj, Long id) throws Exception {
		Pedido pedido;
		try {
			pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

			// Atualiza os dados do pedido com base no obj
			updateData(pedido, obj);

			// Atualiza os itens de pedido
			atualizarItensPedido(pedido, obj);

			// Calcular valor total dos itens de pedido
			calcularValorTotalItens(pedido);

			// Calcular o total da venda
			calcularTotalVenda(pedido);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		return pedidoRepository.save(pedido);
	}

	// Método para atualizar os dados do pedido
	private void updateData(Pedido entity, Pedido obj) {
		entity.setCliente(obj.getCliente());
		entity.setFornecedor(obj.getFornecedor());
		entity.setStatusPedido(obj.getStatusPedido());
		entity.setPagamento(obj.getPagamento());
	}

	// Método para atualizar os itens de pedido
	private void atualizarItensPedido(Pedido entity, Pedido obj) {
		// Lista de itens atualizados
		Set<ItemPedido> itensAtualizados = new HashSet<>();

		// Percorre os itens do objeto atualizado
		for (ItemPedido itemAtualizado : obj.getItens()) {
			// Busca pelo item correspondente no pedido original
			ItemPedido itemExistente = entity.getItens().stream()
					.filter(item -> item.getIdLocal().equals(itemAtualizado.getIdLocal())).findFirst().orElse(null);

			if (itemExistente != null) {
				// Atualiza os dados do item existente
				itemExistente.setQuantidade(itemAtualizado.getQuantidade());
				itemExistente.setValorTotal(itemAtualizado.getValorTotal());
				itensAtualizados.add(itemExistente);
			} else {
				// Se não encontrar o item, adiciona o novo item
				itemAtualizado.setIdPedido(entity);
				itensAtualizados.add(itemAtualizado);
			}
		}

		// Remove os itens que não estão mais presentes no objeto atualizado
		entity.getItens().removeIf(item -> !itensAtualizados.contains(item));
		entity.getItens().addAll(itensAtualizados);
	}

	// Método para calcular o valor total dos itens de pedido
	private void calcularValorTotalItens(Pedido pedido) {
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedidoService.calcularValorTotal(itemPedido);
		}
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
			Pedido pedido = Repository.getReferenceById(id);

			if (pedido == null) {
				throw new ResourceNotFoundException("Pedido não encontrado para o ID fornecido: " + id);
			}

			if (pedido.getStatusPedido() == StatusPedido.EM_TRANSPORTE) {
				throw new StatusEnviadoException("Pedido já em transporte!");
			} else if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
				throw new StatusEnviadoException("Pedido já finalizado!");
			} else if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
				throw new StatusEnviadoException("Pedido já cancelado");
			}

			// Atualiza o estoque antes de enviar o pedido
			for (ItemPedido itemPedido : pedido.getItens()) {
				Estoque estoque = estoqueRespository.findByProduto(itemPedido.getIdProduto().getId());
				if (itemPedido.getQuantidade() > estoque.getQuantidade()) {
					throw new Exception(
							"Estoque insuficiente para o produto com ID: " + itemPedido.getIdProduto().getId());
				}
				estoqueService.atualizarEstoque(itemPedido.getIdProduto().getId(), itemPedido.getQuantidade());
			}

			pedido.setStatusPedido(StatusPedido.EM_TRANSPORTE);
			Pedido pedidoEnviado = Repository.save(pedido);

			return pedidoEnviado;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Pedido não encontrado para o ID fornecido: " + id);
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
