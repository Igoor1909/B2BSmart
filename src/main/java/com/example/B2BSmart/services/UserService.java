package com.example.B2BSmart.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.User;
import com.example.B2BSmart.exceptions.CnpjExistsException;
import com.example.B2BSmart.exceptions.CriptoExistsException;
import com.example.B2BSmart.exceptions.EmailExistsException;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.exceptions.ServiceExc;
import com.example.B2BSmart.repository.UserRepository;
import com.example.B2BSmart.util.Util;


@Service
public class UserService {

    @Autowired
    UserRepository repository;

    // Método para buscar todos os usuários
    public List<User> buscarUsuario() {
        return repository.findAll();
    }

    // Método para inserir um novo usuário
    public User inserirUsuario(User obj) throws Exception {
        try {
            // Verifica se já existe um usuário com o mesmo email
            if (repository.findByEmail(obj.getEmail()) != null) {
                throw new EmailExistsException("Já existe um email cadastrado para " + obj.getEmail() + " ");
            }
            // Verifica se já existe um usuário com o mesmo CNPJ
            if (repository.findByCNPJ(obj.getCNPJ()) != null) {
                throw new CnpjExistsException("Já existe um CNPJ cadastrado para " + obj.getCNPJ() + " ");
            }
            // Criptografa a senha antes de salvar
            obj.setSenha(Util.md5(obj.getSenha()));

        } catch (NoSuchAlgorithmException e) {
            // Exceção se houver erro na criptografia da senha
            throw new CriptoExistsException("Erro na criptografia da senha");
        }

        User user = repository.saveAndFlush(obj);
        return user;
    }

    // Método para alterar um usuário existente
    public User alterarUsuario(User obj, Long id) throws Exception {
        User usuario;
        try {
            // Busca o usuário pelo ID
            usuario = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            // Criptografa a senha antes de salvar
            obj.setSenha(Util.md5(obj.getSenha()));
            // Atualiza os dados do usuário
            updateData(usuario, obj);

        } catch (NoSuchAlgorithmException e) {
            // Exceção se houver erro na criptografia da senha
            throw new CriptoExistsException("Erro na criptografia da senha");
        }
        return repository.save(usuario);
    }

    // Método para atualizar os dados de um usuário
    public void updateData(User entity, User obj) {
        entity.setRazaoSocial(obj.getRazaoSocial());
        entity.setCNPJ(obj.getCNPJ());
        entity.setEmail(obj.getEmail());
        entity.setSenha(obj.getSenha());
        entity.setTipo(obj.getTipo());
        entity.setRua(obj.getRua());
        entity.setEstado(obj.getEstado());
        entity.setBairro(obj.getBairro());
    }

    // Método para excluir um usuário pelo ID
    public void excluirUsuario(Long id) {
        // Busca o usuário pelo ID
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(user);
    }

    // Método para realizar o login do usuário
    public User loginUsuario(String email, String senha) throws ServiceExc {
        // Busca o usuário pelo email e senha fornecidos
        User userLogin = repository.buscarLogin(email, senha);
        return userLogin;
    }
}

