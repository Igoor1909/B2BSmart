package com.example.B2BSmart.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "product")
public class Product {

	//inserindo atributos do produto
	
	//anotation voltado para gerar o ID do produto
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String nome;
	private Double preco;
	private String marca;
	private String descricao;
	private String codigoEAN;
	private Integer quantidadeInicial;
	
	//anotation voltado para gerar o momento exato de certos procedimentos, como criação e atualização
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	public Product() {
		
	}
	
	public Product(Long id, String nome, Double preco, String marca, String descricao,
			Integer quantidadeInicial, Date dataCriacao, String codigoEAN, Date dataAtualizacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.marca = marca;
		this.descricao = descricao;
		this.codigoEAN = codigoEAN;
		this.quantidadeInicial = quantidadeInicial;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
	}



	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}


	public Double getPreco() {
		return preco;
	}

	public String getMarca() {
		return marca;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getQuantidadeInicial() {
		return quantidadeInicial;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	public String getCodigoEAN() {
		return codigoEAN;
	}

	public void setCodigoEAN(String codigoEAN) {
		this.codigoEAN = codigoEAN;
	}

	public void setQuantidadeInicial(Integer quantidadeInicial) {
		this.quantidadeInicial = quantidadeInicial;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
