package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
	List<User> findByCompany(Company company);
}	
