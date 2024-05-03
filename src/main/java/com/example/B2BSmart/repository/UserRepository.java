package com.example.B2BSmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Método para filtrar os usuários pelo email na tabela User
    @Query("select i from User i where i.email = :email")
    public User findByEmail(String email);
    
    // Método para filtrar os usuários pelo CNPJ na tabela User
    @Query("select j from User j where j.CNPJ = :cnpj")
    public User findByCNPJ(@Param("cnpj") String cnpj);
    
    // Método para buscar o login do usuário baseado no email e senha fornecidos
    @Query("select k from User k where k.email = :email and k.senha = :senha")
    public User buscarLogin(@Param("email") String email, @Param("senha") String senha);
}

