package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.enums.BudgetItemStatus;

/** BudgetItemDTO
 * 
 * @author Hugo Andreassa Amaral 
 */
public class BudgetItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String description;

	@NotNull
	@Min(value = 0)
	private Double price;

	@NotBlank
	private Integer quantity;
	
	private String environment;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BudgetItemStatus status;

	@NotNull
	@Min(value = 1)	
	private Long budgetId;

	public BudgetItemDTO() {

	}

	public BudgetItemDTO(String description, Double price, Integer quantity, String environment,
			BudgetItemStatus status, Long budgetId) {
		super();
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.environment = environment;
		this.status = status;
		this.budgetId = budgetId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public BudgetItemStatus getStatus() {
		return status;
	}

	public void setStatus(BudgetItemStatus status) {
		this.status = status;
	}

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}
	
	public BudgetItem toBudgetItem() {
		
		BudgetItem bdItem = new BudgetItem();
		
		bdItem.setDescription(description);
		bdItem.setPrice(price);
		bdItem.setQuantity(quantity);
		bdItem.setEnvironment(environment);
		bdItem.setStatus(status);
		
		Budget bd = new Budget();
		bd.setId(budgetId);
		bdItem.setBudget(bd);
		
		return bdItem;
	}
}
