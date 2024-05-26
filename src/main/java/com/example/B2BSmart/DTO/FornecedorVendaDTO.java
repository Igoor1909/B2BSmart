package com.example.B2BSmart.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.example.B2BSmart.entity.Pedido;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"fornecedorId", "pedidos", "Total_itens_vendidos", "Total_lucro"})
public class FornecedorVendaDTO {

	private Long fornecedorId;
	private int totalQuantidadeItens;
	private BigDecimal totalVendas;
	private List<Pedido> pedidos;

	// getters and setters
	public Long getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public int getTotalQuantidadeItens() {
		return totalQuantidadeItens;
	}

	public void setTotalQuantidadeItens(int totalQuantidadeItens) {
		this.totalQuantidadeItens = totalQuantidadeItens;
	}

	public BigDecimal getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}
