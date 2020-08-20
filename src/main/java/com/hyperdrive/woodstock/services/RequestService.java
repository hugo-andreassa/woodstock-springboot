package com.hyperdrive.woodstock.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.Material;
import com.hyperdrive.woodstock.entities.Request;
import com.hyperdrive.woodstock.entities.RequestItem;
import com.hyperdrive.woodstock.repositories.MaterialRepository;
import com.hyperdrive.woodstock.repositories.RequestItemRepository;
import com.hyperdrive.woodstock.repositories.RequestRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** RequestService
 * 
 * @author Hugo A.
 */
@Service
public class RequestService {
	
	@Autowired
	private RequestRepository repository;
	@Autowired
	private RequestItemRepository itemRepository;
	@Autowired
	private MaterialRepository materialRepository;
	
	public List<Request> findAllByCompanyId(Long companyId) {
		Company company = new Company();
		company.setId(companyId);
		
		return repository.findByCompany(company);
	}
	
	public Request findById(Long id) {
		Optional<Request> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Request insert(Request entity) {
		try {			
			entity.setCreationDate(Instant.now());
			entity = repository.save(entity);
			
			for (RequestItem ri : entity.getItems()) {
				Long materialId = ri.getMaterial().getId();
				Material material = materialRepository.findById(materialId)
						.orElseThrow(() -> new ResourceNotFoundException(materialId));
				
				ri.setMaterial(material);
				ri.setRequest(entity);
			}
			itemRepository.saveAll(entity.getItems());
			
			return entity;
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		} 
	}
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}	
	}
	
	public Request update(Long id, Request entity) {
		try {
			Request obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			
			entity.setId(id);		
			
			for (RequestItem ri : entity.getItems()) {
				Long materialId = ri.getMaterial().getId();
				Material material = materialRepository.findById(materialId)
						.orElseThrow(() -> new ResourceNotFoundException(materialId));
				
				ri.setMaterial(material);
				ri.setRequest(entity);
			}
			// Deleta os itens antigos
			itemRepository.deleteAll(obj.getItems());
			// Salva os itens novos
			itemRepository.saveAll(entity.getItems());
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
