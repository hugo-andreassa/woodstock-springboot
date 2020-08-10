package com.hyperdrive.woodstock.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Project;
import com.hyperdrive.woodstock.repositories.BudgetItemRepository;
import com.hyperdrive.woodstock.repositories.ProjectRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	@Autowired
	private BudgetItemRepository budgetItemRepository;
	
	public List<Project> findAllByBudgetId(Long budgetItemId) {
		Optional<BudgetItem> budgetItem = budgetItemRepository.findById(budgetItemId);
		
		List<Project> list = repository.findByBudgetItem(budgetItem.orElseThrow(() -> new ResourceNotFoundException(budgetItemId)));
		return list;
	}
	
	public Project findById(Long id) {
		Optional<Project> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Project insert(Project entity) {
		try {
			entity.setId(null);				
			
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
	
	public Project update(Long id, Project obj) {
		try {
			Project entity = repository.getOne(id);
			updateData(entity, obj);
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	private void updateData(Project entity, Project obj) {
		entity.setUrl(obj.getUrl());
		entity.setComment(obj.getComment());
	}
}
