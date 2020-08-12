package com.hyperdrive.woodstock.resources;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hyperdrive.woodstock.dto.CuttingPlanDTO;
import com.hyperdrive.woodstock.entities.CuttingPlan;
import com.hyperdrive.woodstock.services.CuttingPlanService;

@RestController
@RequestMapping(value = "/cuttingPlans")
public class CuttingPlanResource {
	
	@Autowired
	private CuttingPlanService service;
	
	@GetMapping()
	public ResponseEntity<List<CuttingPlan>> findAll(
			@RequestParam(value = "budgetItem", defaultValue = "") Long budgetItemId) {
		List<CuttingPlan> list = service.findAllByBudgetId(budgetItemId);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CuttingPlan> findById(@PathVariable Long id) {
		CuttingPlan obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody CuttingPlanDTO dto) {
		CuttingPlan obj = service.insert(dto.toCuttingPlan());
		
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
	public ResponseEntity<CuttingPlan> update(@PathVariable Long id, @RequestBody CuttingPlanDTO dto) {
		service.update(id, dto.toCuttingPlan());
		
		return ResponseEntity.ok().build();
	}
}
