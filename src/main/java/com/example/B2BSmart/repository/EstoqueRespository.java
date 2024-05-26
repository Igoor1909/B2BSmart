package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Estoque;

public interface EstoqueRespository extends JpaRepository<Estoque, Long> {

	@Query("SELECT e FROM Estoque e WHERE e.id_fornecedor.id = :fornecedorId")
	Estoque findByFornecedor(@Param("fornecedorId") Long fornecedorId);

	@Query("SELECT p FROM Estoque p WHERE p.id_produto.id = :produtoId")
	Estoque findByProduto(@Param("produtoId") Long produtoId);
	
	
}

