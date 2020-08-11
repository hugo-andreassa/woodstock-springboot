package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;

public class ClientDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	@NotBlank
	private String phone;
	
	@NotBlank
	private String cpf;
	
	@Min(value = 1)
	private Long companyId;
	
	private Address address;
	
	public ClientDTO() {
		
	}

	public ClientDTO(String name, String email, String phone, String cpf, Long companyId, Address address) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.cpf = cpf;
		this.companyId = companyId;
		this.address = address;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}		
	
	public Client toClient() {
		Client client = new Client();
		
		client.setName(getName());
		client.setEmail(getEmail());
		client.setPhone(getPhone());
		client.setCpf(getCpf());
		client.setCompany(new Company(getCompanyId(), null, null, null, null, null, null));
		client.setAddress(getAddress());
		
		return client;
	}
}
