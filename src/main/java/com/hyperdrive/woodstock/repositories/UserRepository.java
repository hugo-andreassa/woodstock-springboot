package com.hyperdrive.woodstock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM tb_user u WHERE u.email = ?1 and u.password = ?2",
			nativeQuery = true)
	Optional<User> FindByEmailAndPassword(String email, String password);
}	
