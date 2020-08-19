package com.hyperdrive.woodstock.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** Company
 * 
 * @author Hugo A.
 */
@Entity
@Table(name = "tb_company")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String tradingName;
	
	@Column(unique = true, nullable = false)
	private String cnpj;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String whatsapp;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String site;
	
	@Column(nullable = false)
	private String logo;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<User> users = new ArrayList<>();
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<Client> clients = new ArrayList<>();
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<OperatingExpense> expenses = new ArrayList<>();
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<Material> materials = new ArrayList<>();
	
	public Company() {
	
	}

	public Company(Long id, String tradingName, String cnpj, String phone, String whatsapp, 
			String email, String site, String logo, Address address) {
		this.id = id;
		this.tradingName = tradingName;
		this.cnpj = cnpj;
		this.phone = phone;
		this.whatsapp = whatsapp;
		this.email = email;
		this.site = site;
		this.logo = logo;
		this.address = address;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	@JsonIgnore
	public List<User> getUsers() {
		return users;
	}
	
	@JsonIgnore
	public List<Client> getClients() {
		return clients;
	}
	
	@JsonIgnore
	public List<OperatingExpense> getExpenses() {
		return expenses;
	}
	
	@JsonIgnore
	public List<Material> getMaterials() {
		return materials;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
