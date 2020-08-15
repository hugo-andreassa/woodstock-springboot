package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;

/** ClientDTO
 * 
 * @author Hugo Andreassa Amaral 
 */
public class ClientDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	@NotBlank
	private String phone;
	
	@NotBlank
	private String cpfOrCnpj;
	
	@NotNull
	@Min(value = 1)
	private Long companyId;
	
	private Address address;
	
	public ClientDTO() {
		
	}

	public ClientDTO(String name, String email, String phone, String cpfOrCnpj, Long companyId, Address address) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.cpfOrCnpj = cpfOrCnpj;
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

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
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
		client.setCpfOrCnpj(getCpfOrCnpj());
		client.setAddress(getAddress());
		
		Company comp = new Company();
		comp.setId(companyId);
		client.setCompany(comp);
		
		return client;
	}
}
