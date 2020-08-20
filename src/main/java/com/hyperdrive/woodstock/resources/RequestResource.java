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

import com.hyperdrive.woodstock.dto.RequestDTO;
import com.hyperdrive.woodstock.entities.Request;
import com.hyperdrive.woodstock.services.RequestService;

@RestController
@RequestMapping(value = "/requests")
public class RequestResource {
	
	@Autowired
	private RequestService service;
	
	@GetMapping()
	public ResponseEntity<List<Request>> findAll(@RequestParam(name = "company") Long companyId) {
		List<Request> list = service.findAllByCompanyId(companyId);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Request> findById(@PathVariable Long id) {
		Request obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody RequestDTO dto) {
		Request obj = service.insert(dto.toRequest());
		
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
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody RequestDTO dto) {
		service.update(id, dto.toRequest());
		
		return ResponseEntity.ok().build();
	}
}
