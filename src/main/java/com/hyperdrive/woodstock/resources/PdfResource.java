package com.hyperdrive.woodstock.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperdrive.woodstock.services.PdfService;

@RestController
@RequestMapping(value = "/pdf")
public class PdfResource {

	@Autowired
	private PdfService service;

	@GetMapping
	public ResponseEntity<ByteArrayResource> findAll(@RequestParam(value = "company", defaultValue = "") Long companyId,
			@RequestParam(value = "client", defaultValue = "") Long clientId,
			@RequestParam(value = "budget", defaultValue = "") Long budgetId) throws IOException {

		Path path = service.generatePdfFromBudget(companyId, clientId, budgetId);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orcamento.pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

}
