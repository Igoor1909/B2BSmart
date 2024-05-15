package com.example.B2BSmart.entity;

public enum StatusPagamento {

	A_VISTA("A Vista", 1),
	A_PRAZO("A Prazo", 2);
	
	private final String descrição;
	private final int valorInteiro;
	
	private StatusPagamento(String descrição, int valorInteiro) {
		this.descrição = descrição;
		this.valorInteiro = valorInteiro;
	}

	public String getDescrição() {
		return descrição;
	}

	public int getValorInteiro() {
		return valorInteiro;
	}
	
	
	
	
}
