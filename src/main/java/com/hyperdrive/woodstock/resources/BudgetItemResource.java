package com.hyperdrive.woodstock.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.hyperdrive.woodstock.dto.BudgetItemDTO;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.services.BudgetItemService;

@RestController
@RequestMapping(value = "/budgetItems")
public class BudgetItemResource {
	
	@Autowired
	private BudgetItemService service;
	
	@GetMapping()
	public ResponseEntity<List<BudgetItem>> findAll(
			@RequestParam(value = "budget", defaultValue = "") Long budgetId) {
		
		List<BudgetItem> list = service.findAllByBudgetId(budgetId);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BudgetItem> findById(@PathVariable Long id) {
		BudgetItem obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody BudgetItemDTO dto) {
		BudgetItem obj = service.insert(dto.toBudgetItem());
		
		// Cria uma uri com o ID gerado
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BudgetItemDTO dto) {
		service.update(id, dto.toBudgetItem());
		
		return ResponseEntity.ok().build();
	}
}
