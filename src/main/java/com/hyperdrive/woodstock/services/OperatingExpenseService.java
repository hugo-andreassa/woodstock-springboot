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
import com.hyperdrive.woodstock.entities.OperatingExpense;
import com.hyperdrive.woodstock.repositories.OperatingExpenseRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** OperatingExpenseService
 * 
 * @author Hugo A.
 */
@Service
public class OperatingExpenseService {
	
	@Autowired
	private OperatingExpenseRepository repository;
	
	public List<OperatingExpense> findAllByCompanyId(Long companyId) {
		Company company = new Company();
		company.setId(companyId);
		
		return repository.findByCompany(company);
	}
	
	public OperatingExpense findById(Long id) {
		Optional<OperatingExpense> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public OperatingExpense insert(OperatingExpense entity) {
		try {
			entity.setCreationDate(Instant.now());
			
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
	
	public OperatingExpense update(Long id, OperatingExpense entity) {
		try {
			entity.setId(id);
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
