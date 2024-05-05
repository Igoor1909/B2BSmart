package com.example.B2BSmart.entity;


public enum StatusPedido {

	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado"),
	EM_TRANSPORTE("Em_transporte"),
	SOLICITADO("Solicitado");

	public String status;

	private StatusPedido(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	

}
