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

import com.example.B2BSmart.entity.Categoria;
import com.example.B2BSmart.services.CategoriaService;

@RestController
@RequestMapping("B2B/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
    @GetMapping(value = "/buscar")
    public List<Categoria> buscarCategoria() {
        // Chama o serviço para buscar e retornar todos os usuários cadastrados
        return categoriaService.buscarCategoria();
    }
    
    @GetMapping(value = "/buscar/{id}")
    public List<Categoria> buscarPorID(@PathVariable Long id) throws Exception {
        // Chama o serviço para buscar e retornar um usuário pelo ID fornecido
        return categoriaService.buscarPorID(id);
    }
    
    @PostMapping(value = "/inserir")
    public ResponseEntity<String> inserirCategoria(@RequestBody Categoria obj)throws Exception{
    	obj = categoriaService.inserirCategoria(obj);
    	return ResponseEntity.ok("Categoria cadastrada com sucessso");
    }
    
    @PutMapping(value = "/alterar/{id}")
    public ResponseEntity<String> alterarCategoria(@PathVariable Long id, @RequestBody Categoria obj) throws Exception{
    	obj = categoriaService.alterarCategoria(obj, id);
    	return ResponseEntity.ok("Categoria alterada com sucesso");
    }
    
    @DeleteMapping(value = "/excluir/{id}")
    public ResponseEntity<String> excluirCategoria(@PathVariable Long id){
    	categoriaService.excluirCategoria(id);
    	return ResponseEntity.ok("Categoria excluida com sucesso");
    }
    
    
}
