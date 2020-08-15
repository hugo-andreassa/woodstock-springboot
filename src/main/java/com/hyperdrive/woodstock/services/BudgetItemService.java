package com.hyperdrive.woodstock.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.repositories.BudgetItemRepository;
import com.hyperdrive.woodstock.repositories.BudgetRepository;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

/** BudgetItemService
 * 
 * @author Hugo Andreassa Amaral
 */
@Service
public class BudgetItemService {
	
	@Autowired
	private BudgetItemRepository repository;
	@Autowired
	private BudgetRepository budgetRepository;
	
	public List<BudgetItem> findAllByBudgetId(Long budgetId) {
		Optional<Budget> budget = budgetRepository.findById(budgetId);
		
		List<BudgetItem> list = repository.findByBudget(budget.orElseThrow(() -> new ResourceNotFoundException(budgetId)));
		return list;
	}
	
	public BudgetItem findById(Long id) {
		Optional<BudgetItem> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public BudgetItem insert(BudgetItem entity) {
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
	
	public BudgetItem update(Long id, BudgetItem obj) {
		try {
			obj.setId(id);
			
			return repository.save(obj);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (PropertyValueException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
