package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Fornecedor;

public interface FornecedorRespository extends JpaRepository<Fornecedor, Long> {

    // Método para filtrar os usuários do tipo fornecedor pelo email na tabela User
    @Query("select i from Fornecedor i where i.email = :email")
    public Fornecedor findByEmail(String email);
    
    // Método para filtrar os usuários do tipo fornecedor pelo CNPJ na tabela User
    @Query("select j from Fornecedor j where j.CNPJ = :cnpj")
    public Fornecedor findByCNPJ(@Param("cnpj") String cnpj);
    
    // Método para buscar o login do usuário do tipo fornecedor baseado no email e senha fornecidos
    @Query("select k from Fornecedor k where k.email = :email and k.senha = :senha")
    public Fornecedor buscarLogin(@Param("email") String email, @Param("senha") String senha);
}

