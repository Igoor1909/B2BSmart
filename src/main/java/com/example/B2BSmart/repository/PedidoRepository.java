package com.example.B2BSmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Query("SELECT f FROM Pedido f WHERE f.fornecedor.id = :fornecedorId")
	List<Pedido> findByFornecedor(@Param("fornecedorId") Long fornecedorId);


}
