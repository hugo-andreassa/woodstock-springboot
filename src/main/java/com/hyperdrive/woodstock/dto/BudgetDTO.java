package com.hyperdrive.woodstock.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.enums.BudgetStatus;

/** BudgetDTO
 * 
 * @author Hugo Andreassa Amaral 
 */
public class BudgetDTO {
	
	@NotNull
	@Min(value = 10)
	private Integer deadline;
	
	private String deliveryDay;
	
	@NotBlank
	private String paymentMethod;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private BudgetStatus status;
	
	@NotNull
	@Min(value = 1)
	private Long clientId;
	
	private Address address;
	
	public BudgetDTO() {
		
	}

	public BudgetDTO(Integer deadline, String deliveryDay, String paymentMethod, BudgetStatus status, Long clientId,
			Address address) {
		super();
		this.deadline = deadline;
		this.deliveryDay = deliveryDay;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.clientId = clientId;
		this.address = address;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public String getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
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

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Budget toBudget() {
		Budget bud = new Budget();
				
		bud.setDeadline(deadline);
		bud.setDeliveryDay(deliveryDay);
		bud.setPaymentMethod(paymentMethod);
		bud.setStatus(status);
		bud.setAddress(address);	
		
		Client c = new Client();
		c.setId(clientId);
		bud.setClient(c);
		
		return bud;
	}	
}
