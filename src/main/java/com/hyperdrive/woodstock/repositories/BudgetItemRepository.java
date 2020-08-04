package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long>{

	List<BudgetItem> findByBudget(Budget budget);
}
