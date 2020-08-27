package com.hyperdrive.woodstock.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	List<User> findByCompany(Company company);
}	
