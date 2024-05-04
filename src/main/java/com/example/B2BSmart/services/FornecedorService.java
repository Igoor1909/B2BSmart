package com.example.B2BSmart.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Fornecedor;
import com.example.B2BSmart.exceptions.CnpjExistsException;
import com.example.B2BSmart.exceptions.CriptoExistsException;
import com.example.B2BSmart.exceptions.EmailExistsException;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.exceptions.ServiceExc;
import com.example.B2BSmart.repository.FornecedorRespository;
import com.example.B2BSmart.util.Util;


@Service
public class FornecedorService {

    @Autowired
    FornecedorRespository repository;

    // Método para buscar todos os usuários
    public List<Fornecedor> buscarUsuario() {
        return repository.findAll();
    }

    // Método para inserir um novo usuário
    public Fornecedor inserirUsuario(Fornecedor obj) throws Exception {
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

        Fornecedor user = repository.saveAndFlush(obj);
        return user;
    }

    // Método para alterar um usuário existente
    public Fornecedor alterarUsuario(Fornecedor obj, Long id) throws Exception {
        Fornecedor usuario;
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
    public void updateData(Fornecedor entity, Fornecedor obj) {
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
        Fornecedor user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(user);
    }

    // Método para realizar o login do usuário
    public Fornecedor loginUsuario(String email, String senha) throws ServiceExc {
        // Busca o usuário pelo email e senha fornecidos
        Fornecedor userLogin = repository.buscarLogin(email, senha);
        return userLogin;
    }
}

