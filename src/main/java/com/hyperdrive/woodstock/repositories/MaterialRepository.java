package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.Material;

public interface MaterialRepository extends JpaRepository<Material, Long>{
	
	List<Material> findByCompanyOrderByNameAsc(Company company);
}
