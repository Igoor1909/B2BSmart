package com.example.B2BSmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Produto;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // Método para encontrar um produto pelo código EAN
    @Query("select p from Produto p where p.codigo_EAN = :codigo_ean")
    public Produto findByCodigoEAN(@Param("codigo_ean") String codigoEAN);
}


