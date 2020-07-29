package com.hyperdrive.woodstock.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository repository;
	
	public Company findById(Long id) {
		Optional<Company> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Company insert(Company entity) {	
		entity.setId(null);
		return repository.save(entity);
	}
	
	public Company update(Long id, Company obj) {
		try {
			Company entity = repository.getOne(id);
			updateData(entity, obj);
			
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}
	
	private void updateData(Company entity, Company obj) {
		entity.setTradingName(obj.getTradingName());
		entity.setPhone(obj.getPhone());
		entity.setEmail(obj.getEmail());
		entity.setSite(obj.getSite());
		entity.setLogo(obj.getLogo());
	}
}
