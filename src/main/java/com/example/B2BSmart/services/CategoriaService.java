package com.example.B2BSmart.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Categoria;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.CategoriaRespository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRespository repository;

	public List<Categoria> buscarCategoria() {
		return repository.findAll();
	}
	
	public List<Categoria> buscarPorID(Long id) throws Exception {
	    try {
	        // Obtém o pedido correspondente ao ID fornecido
	        Categoria categoria = repository.getReferenceById(id);
	        // Desproxifica o pedido
	        categoria = (Categoria) Hibernate.unproxy(categoria);
	        // Retorna o pedido
	        return Collections.singletonList(categoria);
	    } catch (EntityNotFoundException e) {
	        // Se o pedido não for encontrado, lança uma exceção de recurso não encontrado
	        throw new ResourceNotFoundException(id);
	    }
	}
	
	public Categoria inserirCategoria(Categoria obj) throws Exception {
		Categoria categoria = repository.saveAndFlush(obj);
		return categoria;
		
	}
	
	public Categoria alterarCategoria(Categoria obj, Long id) throws Exception{
		Categoria categoria;
		categoria = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		updateData(categoria, obj);
		return repository.save(categoria);
	}
	
	public void updateData(Categoria entity, Categoria obj) {
		entity.setNome(obj.getNome());
	}
	
	public void excluirCategoria(Long id) {
		Categoria categoria = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		repository.delete(categoria);
	}

}
