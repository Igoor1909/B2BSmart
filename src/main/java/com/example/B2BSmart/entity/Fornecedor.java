package com.example.B2BSmart.entity;


import java.util.Date;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String razaoSocial;
	private String CNPJ;
	private String email;
	private String senha;
	// Usando em ENUM para definir o tipo de usuario, ou FORNECEDOR ou CLIENTE
	private StatusUsuario tipo;
	private String rua;
	private String estado;
	private String bairro;

	
	public Fornecedor() {
		
	}

	public Fornecedor(Long id, String razaoSocial, String cNPJ, String email, String senha, StatusUsuario tipo, String rua,
			String estado, String bairro, Date dataCriacao, Date dataAtualizacao) {
		super();
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.CNPJ = cNPJ;
		this.email = email;
		this.tipo = tipo;
		this.senha = senha;
		this.rua = rua;
		this.estado = estado;
		this.bairro = bairro;

	}

	public Long getId() {
		return id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public StatusUsuario getTipo() {
		return tipo;
	}

	public void setTipo(StatusUsuario tipo) {
		this.tipo = tipo;
	}

	public String getRua() {
		return rua;
	}

	public String getEstado() {
		return estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
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
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(id, other.id);
	}


}
