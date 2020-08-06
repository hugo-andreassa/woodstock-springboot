package com.hyperdrive.woodstock.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.ClientRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	@Autowired
	private AddressRepository adressRepository;
	
	public List<Client> findAll() {
		return repository.findAll();
	}
	
	public Client findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Client insert(Client entity) {
		try {
			
			entity.setId(null);
			entity.getAddress().setId(null);
			Address adrs = adressRepository.save(entity.getAddress());
			
			entity.setAddress(adrs);
			
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
	
	public Client update(Long id, Client obj) {
		try {
			Client entity = repository.getOne(id);
			updateData(entity, obj);
			adressRepository.save(entity.getAddress());
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	private void updateData(Client entity, Client obj) {
		entity.setName(obj.getName());
		entity.setPhone(obj.getPhone());
		entity.setEmail(obj.getEmail());
		entity.setCpf(obj.getCpf());
		
		entity.setAddress(obj.getAddress());
	}
}
