package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.BudgetItem;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long>{

}
