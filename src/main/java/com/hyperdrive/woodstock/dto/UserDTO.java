package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String email;
	
	private String phone;
	
	@NotNull
	private UserStatus status;
	
	@NotNull
	private UserType type;
	
	@NotNull
	@Min(value = 1)
	private Long companyId;

	public UserDTO(String name, String password, String email, String phone,
			UserStatus status, UserType type, Long companyId) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.type = type;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public User toUser() {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		user.setStatus(status);
		user.setType(type);
		
		Company comp = new Company();
		comp.setId(companyId);
		user.setCompany(comp);
			
		return user;
	}

}
