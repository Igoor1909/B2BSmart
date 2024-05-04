package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.Cliente;

public interface ClienteRespository extends JpaRepository<Cliente, Long> {

    // Método para filtrar os usuários do tipo cliente pelo email na tabela User
    @Query("select i from Cliente i where i.email = :email")
    public Cliente findByEmail(String email);
    
    // Método para filtrar os usuários do tipo cliente pelo CNPJ na tabela User
    @Query("select j from Cliente j where j.CNPJ = :cnpj")
    public Cliente findByCNPJ(@Param("cnpj") String cnpj);
    
    // Método para buscar o login do usuário do tipo cliente baseado no email e senha fornecidos
    @Query("select k from Cliente k where k.email = :email and k.senha = :senha")
    public Cliente buscarLogin(@Param("email") String email, @Param("senha") String senha);
}

