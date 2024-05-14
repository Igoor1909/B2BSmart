package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Estoque;
import com.example.B2BSmart.entity.Produto;

public interface EstoqueRespository extends JpaRepository<Estoque, Long> {

	@Query("SELECT k FROM Produto k WHERE k.fornecedor.id = :fornecedorId")
    Produto findByFornecedor(@Param("fornecedorId") Long fornecedorId);

}

