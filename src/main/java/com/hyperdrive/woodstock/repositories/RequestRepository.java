package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
	
	List<Request> findByCompany(Company company);
}
