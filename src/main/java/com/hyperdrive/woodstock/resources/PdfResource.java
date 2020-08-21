package com.hyperdrive.woodstock.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<ByteArrayResource> findAll(@RequestParam(value = "company", defaultValue = "") Long companyId,
			@RequestParam(value = "client", defaultValue = "") Long clientId,
			@RequestParam(value = "budget", defaultValue = "") Long budgetId) throws IOException {

		File file = service.generateBudgetPdf(companyId, clientId, budgetId);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		file.delete();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

}
