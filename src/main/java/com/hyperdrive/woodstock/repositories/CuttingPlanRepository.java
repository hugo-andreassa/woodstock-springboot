package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.CuttingPlan;

public interface CuttingPlanRepository extends JpaRepository<CuttingPlan, Long>{

	List<CuttingPlan> findByBudgetItem(BudgetItem budgetItem);
}
