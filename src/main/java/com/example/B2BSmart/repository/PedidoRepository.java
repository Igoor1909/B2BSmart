package com.example.B2BSmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.B2BSmart.entity.Pedido;




public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}


