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

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.BudgetRepository;
import com.hyperdrive.woodstock.repositories.ClientRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class BudgetService {
	
	@Autowired
	private BudgetRepository repository;
	@Autowired
	private ClientRepository clientRepository;	
	@Autowired
	private AddressRepository addressRepository;
	
	public List<Budget> findAllByClientId(Long clientId) {
		Optional<Client> client = clientRepository.findById(clientId);
		
		List<Budget> list = repository.findByClient(client.orElseThrow(() -> new ResourceNotFoundException(clientId)));
		return list;
	}
	
	public Budget findById(Long id) {
		Optional<Budget> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Budget insert(Budget entity) {
		try {
			entity.setCreationDate(Instant.now());
			
			if(entity.getAddress() != null) {
				Address adrs = addressRepository.save(entity.getAddress());
				entity.setAddress(adrs);
			}			
			
			return repository.save(entity);
		} catch (PropertyValueException e) {
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
	
	public Budget update(Long id, Budget entity) {
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
