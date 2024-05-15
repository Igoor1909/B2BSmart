package com.example.B2BSmart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.B2BSmart.entity.Pagamento;
import com.example.B2BSmart.exceptions.ResourceNotFoundException;
import com.example.B2BSmart.repository.PagamentoRespository;

@Service
public class PagamentoService {

	@Autowired
	PagamentoRespository respository;
	
	public List<Pagamento> buscarPagamentos(){
		return respository.findAll();
	}
	
	public Pagamento inserirPagamento(Pagamento obj)throws Exception {
		Pagamento pagamento = respository.saveAndFlush(obj);
		return pagamento;
	}
	
	public void updateData(Pagamento entity, Pagamento obj) {
		entity.setNome(obj.getNome());
		entity.setTipo(obj.getTipo());
	}
	
	public Pagamento alterarPagamento(Long id, Pagamento obj) throws Exception{
		Pagamento pagamento = respository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		updateData(pagamento, obj);
		return respository.save(pagamento);
	}
	
	public void excluirPagamento(Long id) {
		Pagamento pagamento = respository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		respository.delete(pagamento);
	}
	
}
