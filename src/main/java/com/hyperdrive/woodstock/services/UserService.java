package com.hyperdrive.woodstock.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;
import com.hyperdrive.woodstock.repositories.UserRepository;
import com.hyperdrive.woodstock.security.UserSS;
import com.hyperdrive.woodstock.services.exceptions.AuthorizationException;
import com.hyperdrive.woodstock.services.exceptions.DatabaseException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAllByCompanyId(Long companyId) {
		Company company = new Company();
		company.setId(companyId);
		
		return repository.findByCompany(company);
	}
	
	public User findById(Long id) {
		
		UserSS user = authenticated();
		if(user == null || !user.hasRole(UserType.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<User> opt = repository.findById(id);
		
		return opt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User findByEmail(String email) {
		
		UserSS user = authenticated();
		if(user == null || !user.hasRole(UserType.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<User> opt = repository.findByEmail(email);
		
		return opt.orElseThrow(() -> new UsernameNotFoundException(email));
	}
	
	public User insert(User entity) {
		try {
			
			if(entity.getPassword().isBlank()) {
				throw new DatabaseException("O campo senha não pode ser vazio");
			}
			entity.setPassword(pe.encode(entity.getPassword()));
			
			entity.setStatus(UserStatus.DESATIVADO);
			entity.setType(UserType.MARCENEIRO);
			
			return repository.save(entity);	
		} catch (PropertyValueException e) {
			throw new ResourceNotFoundException(entity.getId());
		} 
	}
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}		
	}
	
	public User update(Long id, User entity) {
		try {
			entity.setId(id);			
			
			User user = repository.findById(id).get();			
			String password = user.getPassword();
			
			if(entity.getPassword() != null) {
				if(!entity.getPassword().isBlank()) {
					password = pe.encode(entity.getPassword());
				}
			}
			entity.setPassword(password);
			
			return repository.save(entity);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}
	
	private UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		} catch (Exception e) {
			return null;
		}		
	}
}
