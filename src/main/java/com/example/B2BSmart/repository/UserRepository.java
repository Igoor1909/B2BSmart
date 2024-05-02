package com.example.B2BSmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.B2BSmart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select i from User i where i.email = :email")
	public User findByEmail(String email);
	
	@Query("select j from User j where j.CNPJ = :cnpj")
	public User findByCNPJ(@Param("cnpj") String cnpj);

}
