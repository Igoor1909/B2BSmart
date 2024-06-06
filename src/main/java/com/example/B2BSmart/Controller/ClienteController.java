package com.example.B2BSmart.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import com.example.B2BSmart.entity.Cliente;
import com.example.B2BSmart.exceptions.ServiceExc;
import com.example.B2BSmart.services.ClienteService;
import com.example.B2BSmart.util.Util;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("B2B/cliente")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService userService;

    // Endpoint para buscar todos os usuários cadastrados
    @GetMapping(value = "/buscar")
    public List<Cliente> buscarUsuario() {
        // Chama o serviço para buscar e retornar todos os usuários cadastrados
        return userService.buscarUsuario();
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping(value = "/buscar/{id}")
    public List<Cliente> buscarPorID(@PathVariable Long id) throws Exception {
        // Chama o serviço para buscar e retornar um usuário pelo ID fornecido
        return userService.buscarPorID(id);
    }

    // Endpoint para cadastrar um novo usuário
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<String> inserirUsuario(@RequestBody Cliente obj) throws Exception {
        // Chama o serviço para inserir um novo usuário e retorna uma mensagem de sucesso
        obj = userService.inserirUsuario(obj);	
        return ResponseEntity.ok("Usuario cadastrado com sucesso!");
    }

    // Endpoint para alterar um usuário existente
    @PutMapping(value = "/alterar/{id}")
    public ResponseEntity<String> alterarUsuario(@PathVariable Long id, @RequestBody Cliente obj) throws Exception {
        // Chama o serviço para alterar um usuário existente e retorna uma mensagem de sucesso
        obj = userService.alterarUsuario(obj, id);
        return ResponseEntity.ok("Usuario alterado com sucesso!");
    }

    // Endpoint para excluir um usuário
    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("id") Long id) {
        // Chama o serviço para excluir um usuário e retorna uma mensagem de sucesso
        userService.excluirUsuario(id);
        return ResponseEntity.ok("Usuario excluído com sucesso!");
    }

    // Endpoint para realizar login
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@Validated @RequestBody Cliente usuario, BindingResult br, HttpSession session)
            throws NoSuchAlgorithmException, ServiceExc {
        // Verifica se houve erros de validação nos campos do objeto Cliente
        if (br.hasErrors()) {
            // Retorna uma resposta com status 400 e uma mensagem de erro se houver erros de validação
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação");
        }

        // Verifica se a senha do cliente não é nula
        if (usuario.getSenha() != null) {
            // Tenta realizar o login do cliente e retorna uma mensagem de sucesso se bem-sucedido
            Cliente userLogin = userService.loginUsuario(usuario.getEmail(), Util.md5(usuario.getSenha()));
            if (userLogin == null) {
                // Retorna uma resposta com status 404 se o cliente não for encontrado
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado. Tente novamente");
            } else {
                // Define o cliente logado na sessão e retorna uma mensagem de sucesso
                session.setAttribute("Usuario Logado", userLogin);
                return ResponseEntity.ok("Login realizado com sucesso");
            }
        } else {
            // Retorna uma resposta com status 400 se a senha do cliente for nula
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha não pode ser nula");
        }
    }
}
