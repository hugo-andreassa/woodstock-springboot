package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hyperdrive.woodstock.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
