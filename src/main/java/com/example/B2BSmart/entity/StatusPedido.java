package com.example.B2BSmart.entity;


public enum StatusPedido {

    FINALIZADO("Finalizado", 1),
    CANCELADO("Cancelado", 2),
    EM_TRANSPORTE("Em_transporte", 3),
    SOLICITADO("Solicitado", 4);

    private final String descricao;
    private final int valorInteiro;

    StatusPedido(String descricao, int valorInteiro) {
        this.descricao = descricao;
        this.valorInteiro = valorInteiro;
    }

    public String getStatus() {
        return descricao;
    }

    public int getValorInteiro() {
        return valorInteiro;
    }
    
    // Método para encontrar o enum a partir de um valor inteiro
    public static StatusPedido getByValorInteiro(int valorInteiro) {
        for (StatusPedido status : StatusPedido.values()) {
            if (status.valorInteiro == valorInteiro) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum StatusPedido encontrado com o valor inteiro: " + valorInteiro);
    }
}

