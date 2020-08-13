package com.hyperdrive.woodstock.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.hyperdrive.woodstock.entities.enums.BudgetStatus;

@Entity
@Table(name = "tb_budget")
public class Budget implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer deadline;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "pt-BR", timezone = "Brazil/East")
	private Instant deliveryDay;
	
	@Column(nullable = false, updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "pt-BR", timezone = "Brazil/East")
	@JsonProperty(access = Access.READ_ONLY)
	private Instant creationDate;
	
	@Column(nullable = false)
	private String paymentMethod;
	
	@Column(nullable = false)
	private BudgetStatus status;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Client client;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "address_id")
	private Address address;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "budget")
	private List<BudgetItem> items;
	
	// TODO: Função de calcular valor total do orçamento
	public Budget() {
		
	}

	public Budget(Long id, Integer deadline, Instant deliveryDay, Instant creationDate, String paymentMethod,
			BudgetStatus status, Client client, Address address) {
		super();
		this.id = id;
		this.deadline = deadline;
		this.deliveryDay = deliveryDay;
		this.creationDate = creationDate;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.client = client;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Instant getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(Instant deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BudgetStatus getStatus() {
		return status;
	}

	public void setStatus(BudgetStatus status) {
		this.status = status;
	}
	
	public List<BudgetItem> getItems() {
		return items;
	}

	@JsonIgnore
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getTotal() {
		double sum = 0;
		
		for (BudgetItem x : items) {
			sum += x.subTotal();
		}
		
		return sum;
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
		Budget other = (Budget) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
