package com.example.B2BSmart.services;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.User;
import com.example.B2BSmart.exceptions.CnpjExistsException;
import com.example.B2BSmart.exceptions.CriptoExistsException;
import com.example.B2BSmart.exceptions.EmailExistsException;
import com.example.B2BSmart.repository.UserRepository;
import com.example.B2BSmart.util.Util;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> buscarUsuario() {
		return repository.findAll();
	}
	
	public User inserirUsuario(User obj) throws Exception  {
		try {
			//Verificação se há um email igual registrado no BD
			if(repository.findByEmail(obj.getEmail()) != null) {
				throw new EmailExistsException("Ja existe um email cadastrado para " + obj.getEmail());
			} 
			if(repository.findByCNPJ(obj.getCNPJ()) != null) {
				throw new CnpjExistsException("Ja existe um CNPJ cadastrado para " + obj.getCNPJ());
			}
				obj.setSenha(Util.md5(obj.getSenha()));
			
			
			
		} catch (NoSuchAlgorithmException e) {
			throw new CriptoExistsException("Erro na criptografia da senha");
		}
		
		obj.setDataCriacao(new Date());
		User user = repository.saveAndFlush(obj);
		return user;
	}
	
	public User alterarUsuario(User obj) {
		obj.setDataAtualizacao(new Date());
		return repository.saveAndFlush(obj);
	}
	
	public void excluirUsuario(Long id) {
		User user = repository.findById(id).get();
		repository.delete(user);
	}
}
