package com.example.B2BSmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Pedido;
import com.example.B2BSmart.entity.StatusPedido;



public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}


