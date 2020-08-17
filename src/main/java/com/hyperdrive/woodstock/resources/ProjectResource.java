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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hyperdrive.woodstock.dto.ProjectDTO;
import com.hyperdrive.woodstock.entities.Project;
import com.hyperdrive.woodstock.services.ProjectService;

@RestController
@RequestMapping(value = "/projects")
public class ProjectResource {
	
	@Autowired
	private ProjectService service;
	
	@GetMapping()
	public ResponseEntity<List<Project>> findAll(
			@RequestParam(value = "budgetItem", defaultValue = "") Long budgetItemId) {
		List<Project> list = service.findAllByBudgetId(budgetItemId);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Project> findById(@PathVariable Long id) {
		Project obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody ProjectDTO dto) {
		Project obj = service.insert(dto.toProject());
		
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
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProjectDTO dto) {
		service.update(id, dto.toProject());
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/picture")
	public ResponseEntity<Void> upload(
			@RequestParam(name = "file") MultipartFile multipartFile, 
			@RequestParam(name = "project") Long projectId) {
		URI uri = service.uploadPicture(multipartFile, projectId);
		
		return ResponseEntity.created(uri).build();
	} 
}
