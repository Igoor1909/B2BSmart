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
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String razaoSocial;
	private String CNPJ;
	private String email;
	private String senha;
	// Usando em ENUM para definir o tipo de usuario, ou FORNECEDOR ou CLIENTE
	private UserRoles tipo;
	private String rua;
	private String estado;
	private String bairro;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	public User() {
		
	}

	public User(Long id, String razaoSocial, String cNPJ, String email, String senha, UserRoles tipo, String rua,
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
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
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

	public UserRoles getTipo() {
		return tipo;
	}

	public void setTipo(UserRoles tipo) {
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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}


}
