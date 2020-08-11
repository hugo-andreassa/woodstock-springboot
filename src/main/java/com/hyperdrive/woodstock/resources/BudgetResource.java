package com.hyperdrive.woodstock.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hyperdrive.woodstock.dto.BudgetDTO;
import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.services.BudgetService;

@RestController
@RequestMapping(value = "/budgets")
public class BudgetResource {
	
	@Autowired
	private BudgetService service;
	
	@GetMapping()
	public ResponseEntity<List<Budget>> findAll(
			@RequestParam(value = "client", defaultValue = "") Long clientId) {
		List<Budget> list = service.findAllByClientId(clientId);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Budget> findById(@PathVariable Long id) {
		Budget obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody BudgetDTO dto) {
		Budget obj = service.insert(dto.toBudget());
		
		// Cria uma uri com o ID gerado
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Budget> update(@PathVariable Long id, @Valid @RequestBody BudgetDTO dto) {
		service.update(id, dto.toBudget());
		
		return ResponseEntity.ok().build();
	}
}
