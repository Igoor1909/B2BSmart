package com.example.B2BSmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.B2BSmart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
