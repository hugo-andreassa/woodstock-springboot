package com.hyperdrive.woodstock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.repositories.UserRepository;
import com.hyperdrive.woodstock.security.UserSS;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		UserSS userSS = new UserSS(user.getId(), user.getPassword(), user.getEmail(), user.getStatus(), user.getType());
		return userSS;
	}	

}
