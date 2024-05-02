package com.example.B2BSmart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.B2BSmart.entity.User;
import com.example.B2BSmart.services.UserService;

@RestController
@RequestMapping("B2B/usuario")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public List<User> buscarUsuario(){
		return userService.buscarUsuario();
	}
	
	@PostMapping("/")
	public User inserirUsuario(@RequestBody User obj) throws Exception {
		return userService.inserirUsuario(obj);
	}
	
	@PutMapping("/{id}")
	public User alterarUsuario(@RequestBody User obj) {
		return userService.alterarUsuario(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long id){
		userService.excluirUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	

}
