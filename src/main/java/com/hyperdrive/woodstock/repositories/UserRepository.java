package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
}	
