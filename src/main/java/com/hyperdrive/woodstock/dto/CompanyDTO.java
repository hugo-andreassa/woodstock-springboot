package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Company;

/** CompanyDTO
 * 
 * @author Hugo Andreassa Amaral 
 */
public class CompanyDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String tradingName;
	
	@NotBlank
	@CNPJ
	private String cnpj;
	
	@NotBlank
	private String phone;
	
	@NotBlank
	private String whatsapp;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String site;
	
	@NotBlank
	private String logo;
	
	@NotNull
	private Address address;
	
	public CompanyDTO() {
		
	}

	public CompanyDTO(String tradingName, String cnpj, String phone, 
			String whatsapp, String email, String site, String logo,
			Address address) {
		super();
		this.tradingName = tradingName;
		this.cnpj = cnpj;
		this.phone = phone;
		this.whatsapp = whatsapp;
		this.email = email;
		this.site = site;
		this.logo = logo;
		this.address = address;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * 
	 * @return Retorna um objeto Company a partir do DTO
	 */
	public Company toCompany() {
		
		Company company = new Company();
		company.setTradingName(tradingName);
		company.setCnpj(cnpj);
		company.setPhone(phone);
		company.setWhatsapp(whatsapp);
		company.setEmail(email);
		company.setSite(site);
		company.setLogo(logo);
		company.setAddress(address);
		
		return company;
	}
}
