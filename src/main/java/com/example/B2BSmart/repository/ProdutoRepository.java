package com.example.B2BSmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Product;


public interface ProdutoRepository extends JpaRepository<Product, Long> {
    
    // Método para encontrar um produto pelo código EAN
    @Query("select i from Product i where i.codigo_EAN = :codigo_ean")
    public Product findByCodigoEAN(@Param("codigo_ean") String codigo_EAN);
}

