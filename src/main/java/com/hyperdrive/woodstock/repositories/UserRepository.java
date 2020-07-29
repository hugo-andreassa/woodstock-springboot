package com.hyperdrive.woodstock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// Teste para o Login
	Optional<User> findByEmailAndPassword(String email, String password);
}	
