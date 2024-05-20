package com.example.B2BSmart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant dataHora;

	private StatusPedido statusPedido;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;
	
	@ManyToOne
	@JoinColumn(name = "id_pagamento")
	private Pagamento pagamento;
	
	
	@OneToMany(mappedBy = "idPedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemPedido> itens = new HashSet<>();
	
	@JoinColumn(name = "total_venda")
	private BigDecimal totalVenda;
	
	
	public Pedido() {

	}

	

	public Pedido(Long id, StatusPedido statusPedido, Cliente cliente, Fornecedor fornecedor,
			Pagamento pagamento, BigDecimal totalVenda) {
		super();
		this.id = id;
		this.dataHora = Instant.now();
		this.statusPedido = statusPedido;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.pagamento = pagamento;
		this.totalVenda = totalVenda;
	}



	public Long getId() {
		return id;
	}

	public Instant getDataHora() {
		return dataHora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataHora(Instant dataHora) {
		this.dataHora = dataHora;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);

	}


	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}



	public BigDecimal getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}



	
	

}
