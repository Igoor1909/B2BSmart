package com.example.B2BSmart.Controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.B2BSmart.entity.Cliente;
import com.example.B2BSmart.entity.Fornecedor;
import com.example.B2BSmart.exceptions.ServiceExc;
import com.example.B2BSmart.services.FornecedorService;
import com.example.B2BSmart.util.Util;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("B2B/fornecedor")
public class FornecedorController {

	// Injeção de dependência do UserService para acesso aos métodos de serviço relacionados ao usuário
	@Autowired
	private FornecedorService userService;

	// Mapeamento do endpoint "/buscar" para o método buscarUsuario usando o método GET
	@GetMapping(value = "/buscar")
	public List<Fornecedor> buscarUsuario() {
	    // Chama o serviço userService para buscar e retornar todos os usuários cadastrados
	    return userService.buscarUsuario();
	}

	// Mapeamento do endpoint "/cadastrar" para o método inserirUsuario usando o método POST
	@PostMapping(value = "/cadastrar")
	public Fornecedor inserirUsuario(@RequestBody Fornecedor obj) throws Exception {
	    // Chama o serviço userService para inserir um novo usuário utilizando os dados fornecidos no corpo da requisição
	    return userService.inserirUsuario(obj);
	}

	// Mapeamento do endpoint "/alterar/{id}" para o método alterarUsuario usando o método PUT
	@PutMapping(value = "/alterar/{id}")
	public ResponseEntity<Fornecedor> alterarUsuario(@PathVariable Long id, @RequestBody Fornecedor obj) throws Exception {
	    // Chama o serviço userService para alterar o usuário com o ID fornecido, utilizando os dados do objeto User recebido no corpo da requisição
	    obj = userService.alterarUsuario(obj, id);
	    // Retorna uma resposta com status 200 (OK) e o objeto User alterado no corpo da resposta
	    return ResponseEntity.ok().body(obj);
	}


	// Mapeamento do endpoint "/deletar/{id}" para o método excluirUsuario usando o método DELETE
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long id) {
	    // Chama o serviço userService para excluir o usuário com o ID fornecido
	    userService.excluirUsuario(id);
	    // Retorna uma resposta com status 200 (OK) indicando que o usuário foi excluído com sucesso
	    return ResponseEntity.ok().build();
	}

	// Mapeamento do endpoint "/login" para o método login usando o método POST
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@Validated @RequestBody Cliente usuario, BindingResult br, HttpSession session)
			throws NoSuchAlgorithmException, ServiceExc {
		// Verifica se houve erros de validação nos campos do objeto User
		if (br.hasErrors()) {
			// Se houver erros, retorna uma resposta com status 400 (BAD_REQUEST) e uma
			// mensagem de erro
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação");
		}

		// Verifica se a senha do usuário não é nula
		if (usuario.getSenha() != null) {
			// Tenta realizar o login do usuário utilizando o serviço userService
			Fornecedor userLogin = userService.loginUsuario(usuario.getEmail(), Util.md5(usuario.getSenha()));
			// Verifica se o usuário não foi encontrado
			if (userLogin == null) {
				// Se o usuário não foi encontrado, retorna uma resposta com status 404
				// (NOT_FOUND) e uma mensagem
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado. Tente novamente");
			} else {
				// Se o usuário foi encontrado, define o usuário logado na sessão e retorna uma
				// resposta com status 200 (OK) e uma mensagem de sucesso
				session.setAttribute("Usuario Logado", userLogin);
				return ResponseEntity.ok("Login realizado com sucesso");
			}
		} else {
			// Se a senha do usuário for nula, retorna uma resposta com status 400
			// (BAD_REQUEST) e uma mensagem de erro
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode ser nula");
		}
	}

}
