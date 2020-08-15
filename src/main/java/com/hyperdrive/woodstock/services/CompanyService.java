package com.hyperdrive.woodstock.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** CompanyService
 * 
 * @author Hugo Andreassa Amaral
 */
@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository repository;
	@Autowired
	private AddressRepository addressRepository;
	
	public Company findById(Long id) {
		Optional<Company> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Company insert(Company entity) {	
		try {
			
			if(entity.getAddress() != null) {
				Address adrs = addressRepository.save(entity.getAddress());
				entity.setAddress(adrs);
			}
			
			return repository.save(entity);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		} 
		
	}
	
	public Company update(Long id, Company entity) {
		try {
			entity.setId(id);
			
			if(entity.getAddress() != null) {
				Address adrs = addressRepository.save(entity.getAddress());
				entity.setAddress(adrs);
			}
			
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
}
