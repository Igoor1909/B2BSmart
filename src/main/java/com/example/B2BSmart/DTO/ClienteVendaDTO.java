package com.example.B2BSmart.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.example.B2BSmart.entity.Pedido;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "clienteId", "pedidos", "total_itens_comprados", "total_prejuizo" })
public class ClienteVendaDTO {

	private Long clienterId;
	private int totalQuantidadeItens;
	private BigDecimal totalVendas;
	private List<Pedido> pedidos;

	public Long getClienterId() {
		return clienterId;
	}

	public int getTotalQuantidadeItens() {
		return totalQuantidadeItens;
	}

	public BigDecimal getTotalVendas() {
		return totalVendas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setClienterId(Long clienterId) {
		this.clienterId = clienterId;
	}

	public void setTotalQuantidadeItens(int totalQuantidadeItens) {
		this.totalQuantidadeItens = totalQuantidadeItens;
	}

	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
