package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/*
	
	Teste para o Login 
	
	@Query(value = "SELECT * FROM tb_user u WHERE u.status = 0 and (u.email = ?1 and u.password = ?2)",
	 
			nativeQuery = true)
	Optional<User> FindByEmailAndPassword(String email, String password);
	
	*/
}	
