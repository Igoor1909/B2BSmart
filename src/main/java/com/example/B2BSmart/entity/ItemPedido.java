package com.example.B2BSmart.entity;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLocal;
	
	
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	@JsonIgnore
	private Pedido idPedido;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto idProduto;
	
	
	private Integer quantidade;
	
	@Column(precision = 8, scale = 2)
	private BigDecimal valorTotal;
	
	public ItemPedido() {
		
	}

	public ItemPedido(Long idLocal, Pedido idPedido, Produto idProduto, Integer quantidade, BigDecimal valorTotal) {
		super();
		this.idLocal = idLocal;
		this.idPedido = idPedido;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public Pedido getIdPedido() {
		return idPedido;
	}

	public Produto getIdProduto() {
		return idProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdPedido(Pedido idPedido) {
		this.idPedido = idPedido;
	}

	public void setIdProduto(Produto idProduto) {
		this.idProduto = idProduto;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLocal, idPedido, idProduto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(idLocal, other.idLocal) && Objects.equals(idPedido, other.idPedido)
				&& Objects.equals(idProduto, other.idProduto);
	}
	
	
	
}
