package com.hyperdrive.woodstock.services;

import java.time.Instant;
import java.util.Collections;
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
import com.hyperdrive.woodstock.repositories.MaterialRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** Material Service
 * 
 * @author Hugo A.
 */
@Service
public class MaterialService {
	
	@Autowired
	private MaterialRepository repository;
	
	public List<Material> findAllByCompanyId(Long companyId) {
		Company company = new Company();
		company.setId(companyId);
		
		List<Material> materials = repository.findByCompany(company);
		Collections.sort(materials);
		
		return materials;
	}
	
	public Material findById(Long id) {
		Optional<Material> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Material insert(Material entity) {
		try {			
			entity.setLastUpdate(Instant.now());
			
			return repository.save(entity);
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
	
	public Material update(Long id, Material entity) {
		try {
			entity.setId(id);
			entity.setLastUpdate(Instant.now());
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
