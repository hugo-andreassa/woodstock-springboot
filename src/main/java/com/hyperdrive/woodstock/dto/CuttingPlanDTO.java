package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.CuttingPlan;

public class CuttingPlanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	private Double height;
	
	@NotNull
	private Double width;
	
	@NotNull
	private Integer quantity;
	
	private String comment;
	
	@NotNull
	@Min(value = 1)
	private Long budgetItemId;
	
	public CuttingPlanDTO() {
		
	}

	public CuttingPlanDTO(Double height, Double width, Integer quantity, String comment, Long budgetItemId) {
		super();
		this.height = height;
		this.width = width;
		this.quantity = quantity;
		this.comment = comment;
		this.budgetItemId = budgetItemId;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBudgetItemId() {
		return budgetItemId;
	}

	public void setBudgetItemId(Long budgetItemId) {
		this.budgetItemId = budgetItemId;
	}
	
	public CuttingPlan toCuttingPlan() {
		CuttingPlan cutt = new CuttingPlan();
		
		cutt.setHeight(height);
		cutt.setWidth(width);
		cutt.setQuantity(quantity);
		cutt.setComment(comment);
		
		BudgetItem bdItem = new BudgetItem();
		bdItem.setId(budgetItemId);
		cutt.setBudgetItem(bdItem);
		
		return cutt;
	}	
}
