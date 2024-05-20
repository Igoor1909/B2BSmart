package com.example.B2BSmart.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.ItemPedido;
import com.example.B2BSmart.entity.Produto;
import com.example.B2BSmart.repository.ItemPedidoRepository;
import com.example.B2BSmart.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	// Método para calcular o valor total do item com base no preço do produto
    public void calcularValorTotal(ItemPedido itemPedido) {
        if (itemPedido.getIdProduto() != null && itemPedido.getQuantidade() != null) {
            Produto produto = itemPedido.getIdProduto();
            produto = produtoRepository.findById(produto.getId()).orElse(null); // Busca o produto no banco, se necessário

            if (produto != null) {
                BigDecimal precoUnitario = produto.getPreco();  // Supondo que produto.getPreco() retorna um BigDecimal
                Integer quantidade = itemPedido.getQuantidade();
                
                // Converter a quantidade para BigDecimal
                BigDecimal quantidadeDecimal = BigDecimal.valueOf(quantidade);
                
                // Realizar a multiplicação
                BigDecimal valorTotal = quantidadeDecimal.multiply(precoUnitario);
                
                // Definir o valor total no itemPedido
                itemPedido.setValorTotal(valorTotal);
            }
        }
    }
}
