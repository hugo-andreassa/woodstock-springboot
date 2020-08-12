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
import com.hyperdrive.woodstock.entities.CuttingPlan;
import com.hyperdrive.woodstock.repositories.BudgetItemRepository;
import com.hyperdrive.woodstock.repositories.CuttingPlanRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class CuttingPlanService {
	
	@Autowired
	private CuttingPlanRepository repository;
	@Autowired
	private BudgetItemRepository budgetItemRepository;
	
	public List<CuttingPlan> findAllByBudgetId(Long budgetItemId) {
		Optional<BudgetItem> budgetItem = budgetItemRepository.findById(budgetItemId);
		
		List<CuttingPlan> list = repository.findByBudgetItem(budgetItem.orElseThrow(() -> new ResourceNotFoundException(budgetItemId)));
		return list;
	}
	
	public CuttingPlan findById(Long id) {
		Optional<CuttingPlan> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public CuttingPlan insert(CuttingPlan entity) {
		try {				
			
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
	
	public CuttingPlan update(Long id, CuttingPlan entity) {
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
