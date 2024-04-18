package com.example.B2BSmart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	//Metodo para consultar os usuarios criados para realizar as requisições
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username);
	}

}
