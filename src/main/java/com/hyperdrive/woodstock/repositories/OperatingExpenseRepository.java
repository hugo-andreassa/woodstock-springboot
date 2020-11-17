package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.OperatingExpense;

public interface OperatingExpenseRepository extends JpaRepository<OperatingExpense, Long>{
	
	List<OperatingExpense> findByCompanyOrderByIdDesc(Company company);
}
