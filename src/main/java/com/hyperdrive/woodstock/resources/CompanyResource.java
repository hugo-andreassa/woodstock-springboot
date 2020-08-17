package com.hyperdrive.woodstock.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.hyperdrive.woodstock.dto.CompanyDTO;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.services.CompanyService;

@RestController
@RequestMapping(value = "/companies")
public class CompanyResource {
	
	@Autowired
	private CompanyService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Company> findById(@PathVariable Long id) {
		Company obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CompanyDTO dto) {
		Company obj = service.insert(dto.toCompany());
		
		// Cria uma uri com o ID gerado
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Company> update(@PathVariable Long id, @Valid @RequestBody CompanyDTO dto) {
		service.update(id, dto.toCompany());
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/picture")
	public ResponseEntity<Void> upload(
			@RequestParam(name = "file") MultipartFile multipartFile, 
			@RequestParam(name = "company") Long companyId) {
		URI uri = service.uploadPicture(multipartFile, companyId);
		
		return ResponseEntity.created(uri).build();
	} 
}
