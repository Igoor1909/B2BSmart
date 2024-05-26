package com.example.B2BSmart.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.DTO.ClienteVendaDTO;
import com.example.B2BSmart.DTO.FornecedorVendaDTO;
import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.entity.ItemPedido;
import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.entity.StatusPedido;
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
		
		// Definir a data e hora atuais
	    pedido.setDataHora(Instant.now());
		// Associar cada item ao pedido antes de salvar
		pedido.setStatusPedido(StatusPedido.SOLICITADO);
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
			// Obtém a referência do pedido pelo ID
			Pedido pedido = Repository.getReferenceById(id);

			// Verifica se o pedido existe
			if (pedido == null) {
				// Lança uma exceção se o pedido não for encontrado
				throw new ResourceNotFoundException("Pedido não encontrado para o ID fornecido: " + id);
			}
			// Verifica o status atual do pedido
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
			// Atualiza o estoque antes de enviar o pedido
			for (ItemPedido itemPedido : pedido.getItens()) {
				// Busca o estoque do produto
				Estoque estoque = estoqueRespository.findByProduto(itemPedido.getIdProduto().getId());
				// Verifica se há quantidade suficiente no estoque
				if (itemPedido.getQuantidade() > estoque.getQuantidade()) {
					// Lança uma exceção se o estoque for insuficiente
					throw new Exception(
							"Estoque insuficiente para o produto com ID: " + itemPedido.getIdProduto().getId());
				}
				// Atualiza o estoque com a quantidade do item pedido
				estoqueService.atualizarEstoque(itemPedido.getIdProduto().getId(), itemPedido.getQuantidade());
			}
			// Define o status do pedido para "EM_TRANSPORTE"
			pedido.setStatusPedido(StatusPedido.EM_TRANSPORTE);
			// Salva o pedido atualizado
			Pedido pedidoEnviado = Repository.save(pedido);
			// Retorna o pedido enviado
			return pedidoEnviado;
		} catch (EntityNotFoundException e) {
			// Lança uma exceção se o pedido não for encontrado
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

	public FornecedorVendaDTO getVendaInfoPorFornecedor(Long fornecedorId) {
		List<Pedido> pedidos = pedidoRepository.findAll();

		int totalQuantidadeItens = 0;
		BigDecimal totalVendas = BigDecimal.ZERO;

		// Filtrar pedidos pelo ID do fornecedor
		List<Pedido> pedidosFiltrados = pedidos.stream()
				.filter(pedido -> pedido.getFornecedor().getId().equals(fornecedorId)).collect(Collectors.toList());

		for (Pedido pedido : pedidosFiltrados) {
			BigDecimal totalVenda = pedido.getTotalVenda();
			if (totalVenda != null) {
				totalVendas = totalVendas.add(totalVenda);
			}
			for (ItemPedido item : pedido.getItens()) {
				Integer quantidade = item.getQuantidade();
				if (quantidade != null) {
					totalQuantidadeItens += quantidade;
				}
			}
		}

		FornecedorVendaDTO fornecedorVendaInfoDTO = new FornecedorVendaDTO();
		fornecedorVendaInfoDTO.setFornecedorId(fornecedorId);
		fornecedorVendaInfoDTO.setTotalQuantidadeItens(totalQuantidadeItens);
		fornecedorVendaInfoDTO.setTotalVendas(totalVendas);
		fornecedorVendaInfoDTO.setPedidos(pedidosFiltrados);

		return fornecedorVendaInfoDTO;
	}

	public ClienteVendaDTO getVendaInfoPorCliente(Long clienteId) {
		// Obtem todos os pedidos do repositório
		List<Pedido> pedidos = pedidoRepository.findAll();

		// Inicializa as variáveis para a quantidade total de itens e o valor total das vendas
		int totalQuantidadeItens = 0;
		BigDecimal totalVendas = BigDecimal.ZERO;

		// Filtra a lista de pedidos para incluir apenas aqueles feitos pelo cliente com o ID especificado
		List<Pedido> pedidosFiltrados = pedidos.stream().filter(pedido -> pedido.getCliente().getId().equals(clienteId))
				.collect(Collectors.toList());

		// Itera sobre os pedidos filtrados
		for (Pedido pedido : pedidosFiltrados) {
			// Obtem o valor total da venda para o pedido atual
			BigDecimal totalVenda = pedido.getTotalVenda();
			// Se o valor total da venda não for nulo, adiciona-o ao totalVendas
			if (totalVenda != null) {
				totalVendas = totalVendas.add(totalVenda);
			}
			// Itera sobre os itens do pedido atual
			for (ItemPedido item : pedido.getItens()) {
				// Obtem a quantidade de itens do pedido atual
				Integer quantidade = item.getQuantidade();
				// Se a quantidade não for nula, adiciona-a ao totalQuantidadeItens
				if (quantidade != null) {
					totalQuantidadeItens += quantidade;
				}
			}
		}

		// Cria uma nova instância de ClienteVendaDTO para armazenar os dados agregados
		ClienteVendaDTO clienteVendaInfoDTO = new ClienteVendaDTO();
		// Define o ID do cliente no DTO
		clienteVendaInfoDTO.setClienterId(clienteId);
		// Define a quantidade total de itens no DTO
		clienteVendaInfoDTO.setTotalQuantidadeItens(totalQuantidadeItens);
		// Define o valor total das vendas no DTO
		clienteVendaInfoDTO.setTotalVendas(totalVendas);
		// Define a lista de pedidos filtrados no DTO
		clienteVendaInfoDTO.setPedidos(pedidosFiltrados);

		// Retorna o DTO contendo as informações agregadas de vendas para o cliente especificado
		return clienteVendaInfoDTO;
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
