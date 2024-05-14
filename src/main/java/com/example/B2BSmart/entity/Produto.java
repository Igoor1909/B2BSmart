package com.example.B2BSmart.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	
	private static final long serialVersionUID = 1L;


	//inserindo atributos do produto
	
	//anotation voltado para gerar o ID do produto
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String nome;
	private Double preco;
	private String marca;
	private String descricao;
	private String codigo_EAN;
	
	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;
	
	
	@ManyToMany
	@JoinTable(name = "CategoriaProduto", 
	joinColumns = @JoinColumn(name = "IdProduto"),
	inverseJoinColumns = @JoinColumn(name = "IdCategoria"))
	private Set<Categoria> categorias = new HashSet<>();
	
		
	public Produto() {
		
	}
	
	public Produto(Long id, String nome, Double preco, String marca, String descricao,
			Integer quantidadeInicial, Date dataCriacao, String codigo_EAN, Date dataAtualizacao, Fornecedor fornecedor) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.marca = marca;
		this.descricao = descricao;
		this.codigo_EAN = codigo_EAN;
		this.fornecedor = fornecedor;
		
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
		return codigo_EAN;
	}

	public void setCodigoEAN(String codigo_EAN) {
		this.codigo_EAN = codigo_EAN;
	}
	
	

	public String getCodigo_EAN() {
		return codigo_EAN;
	}


	public void setCodigo_EAN(String codigo_EAN) {
		this.codigo_EAN = codigo_EAN;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}



	

	
	
	
}
