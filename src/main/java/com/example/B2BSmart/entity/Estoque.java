package com.example.B2BSmart.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_local;
	
	@ManyToOne
	private Produto id_produto;
	@ManyToOne
	private Fornecedor id_fornecedor;
	
	private Integer quantidade;
	
	public Estoque() {
		
	}
	
	public Estoque(Long id_local, Produto id_produto, Fornecedor id_fornecedor, Integer quantidade) {
		super();
		this.id_local = id_local;
		this.id_produto = id_produto;
		this.id_fornecedor = id_fornecedor;
		this.quantidade = quantidade;
	}
	
	public Produto getId_produto() {
		return id_produto;
	}
	public Fornecedor getId_fornecedor() {
		return id_fornecedor;
	}
	public void setId_produto(Produto id_produto) {
		this.id_produto = id_produto;
	}
	public void setId_fornecedor(Fornecedor id_fornecedor) {
		this.id_fornecedor = id_fornecedor;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id_fornecedor, id_local, id_produto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		return Objects.equals(id_fornecedor, other.id_fornecedor) && Objects.equals(id_local, other.id_local)
				&& Objects.equals(id_produto, other.id_produto);
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	

}
