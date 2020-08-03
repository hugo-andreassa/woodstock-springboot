package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.Client;

public interface BudgetRepository extends JpaRepository<Budget, Long>{
	
	List<Budget> findByClient(Client client);
}
