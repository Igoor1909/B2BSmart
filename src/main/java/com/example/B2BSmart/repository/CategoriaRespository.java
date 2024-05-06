package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Categoria;
import com.example.B2BSmart.entity.Cliente;

public interface CategoriaRespository extends JpaRepository<Categoria, Long> {


}

