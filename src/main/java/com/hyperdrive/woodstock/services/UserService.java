package com.hyperdrive.woodstock.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> opt = repository.findById(id);
		
		return opt.get();
	}	
}
