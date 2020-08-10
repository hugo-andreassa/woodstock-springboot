package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	List<Project> findByBudgetItem(BudgetItem budgetItem);
}
